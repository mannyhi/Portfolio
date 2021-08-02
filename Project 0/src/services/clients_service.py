# clients service consist of data transformation and logic code pertaining to it
import src.dao.client_dao as cdao
from src.models.clients import Clients
from src.models.accounts import Accounts
import logging
logging.basicConfig(filename='clients.log', level=logging.INFO)

# function to call create function in client DAO
def create_new_client(client):
    logging.info('CS: create new client')
    cdao.create_new_client(client)

# function to retrieve all client_ids
def get_all_clients(client_id):
    logging.info('CS: get all clients')
    db_clients = cdao.get_client_by_id(client_id)
    client_dict = {}
    for client in db_clients:
        client_dict[client[0]] = Clients(client[0], client[1], client[2], client[3], client[4])
    return client_dict

# function to UDC: update, delete or create client_id
def udc_client(client_id, json_dict, action):
    logging.info(f'CS: {action} client')
    if cdao.get_client_by_id(client_id):
        if action == "PUT":
            cdao.update_client(client_id, json_dict)
        elif action == "DELETE":
            cdao.delete_client_by_id(client_id)
        elif action == "POST":
            cdao.create_new_account(json_dict)
        return True
    else:
        return None

# function to get all accounts or get accounts in a specific range of a client_id
def get_accounts(client_id, agt, alt, action):
    logging.info(f'CS: {action}')
    if cdao.get_client_by_id(client_id):
        account_dict = {}
        db_accounts = {}
        if action == "all accounts":
            db_accounts = cdao.get_all_accounts(client_id)
        elif action == "account ranges":
            db_accounts = cdao.get_accounts_by_range(client_id, agt, alt)
        for account in db_accounts:
            account_dict[account[0]] = Accounts(account[0], account[1], account[2], account[3])
        return account_dict
    else:
        return None

# function that either retrieves, updates or delete a specific account_id
def get_account_x(client_id, account_id, passing_account, action):
    logging.info(f'CS: {action}')
    if cdao.get_client_by_id(client_id):
        account_dict = {}
        db_accounts = cdao.get_account_x(client_id, account_id)
        for account in db_accounts:
            account_dict[account[0]] = Accounts(account[0], account[1], account[2], account[3])
        if action == "GET":
            return account_dict
        elif action == "PUT" and account_dict:
            cdao.update_account(client_id, account_id, passing_account)
            return True
        elif action == "DELETE" and account_dict:
            cdao.delete_account(client_id, account_id)
            return True
        else:
            return account_dict
    else:
        return None

# function that either withdraw or deposit into an account_id of a client_id
def transaction(client_id, account_id, amount):
    logging.info('CS: withdraw/deposit')
    if cdao.get_client_by_id(client_id):
        acct_dict = get_account_x(client_id, account_id, {}, "")
        if acct_dict:
            if 'withdraw' in amount.keys() and amount['withdraw'] <= acct_dict[account_id].get_balance():
                passing_acct_dict = {'account_type': acct_dict[account_id].get_account_type(),
                                     'balance': (acct_dict[account_id].get_balance() - amount['withdraw'])}
                cdao.update_account(client_id, account_id, passing_acct_dict)
                return True
            elif 'deposit' in amount.keys():
                passing_acct_dict = {'account_type': acct_dict[account_id].get_account_type(),
                                     'balance': (amount['deposit'] + acct_dict[account_id].get_balance())}
                cdao.update_account(client_id, account_id, passing_acct_dict)
                return True
            else:
                return 422
    else:
        return None

# function that transfer an amount from one account_id to the other account_id all from same client_ID
def transfer(client_id, account_id, account_id_2, amount):
    logging.info('CS: transfer')
    if cdao.get_client_by_id(client_id):
        from_acct = get_account_x(client_id, account_id, {}, "")
        to_acct = get_account_x(client_id, account_id_2, {}, "")
        if from_acct and to_acct:
            if amount['amount'] <= from_acct[account_id].get_balance():
                passing_acct_dict1 = {'account_type': from_acct[account_id].get_account_type(),
                                 'balance': (from_acct[account_id].get_balance() - amount['amount'])}
                cdao.update_account(client_id, account_id, passing_acct_dict1)
                passing_acct_dict2 = {'account_type': to_acct[account_id_2].get_account_type(),
                                 'balance': (amount['amount'] + to_acct[account_id_2].get_balance())}
                cdao.update_account(client_id, account_id_2, passing_acct_dict2)
                return True
            else:
                return 422
    else:
        return None