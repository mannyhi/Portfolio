# accessing DB data and executing SQL statements
# DB data = two tables: clients and accounts having a 1 to Many relationship

# import to establish database connection
from src.utils.dbconfigs import get_connection

import logging
logging.basicConfig(filename='clients.log', level=logging.INFO)

# note: functions throughout this module have access to database accompanied with SQL executions

# function that creates a new client on DB
def create_new_client(client):
    logging.info('DAO: create new client')
    try:
        db_connection = get_connection()
        db_cursor = db_connection.cursor()
        db_cursor.execute("insert into clients values(default, ?, ?, ?, ?)", (client['first_name'], client['last_name'], client['email'], client['password']))
        db_connection.commit()
    finally:
        db_connection.close()

# function that pulls client from DB by client id
def get_client_by_id(client_id):
    logging.info('DAO: get client by ID')
    query_rows = None
    try:
        db_connection = get_connection()
        db_cursor = db_connection.cursor()
        if client_id:
            db_cursor.execute(f"select * from clients where client_id={client_id}")
        else:
            db_cursor.execute("select * from clients")
        query_rows = db_cursor.fetchall()
    finally:
        db_connection.close()
    return query_rows

# function updates client's information
def update_client(client_id, client):
    logging.info('DAO: update client')
    try:
        db_connection = get_connection()
        db_cursor = db_connection.cursor()
        db_cursor.execute(f"update clients set first_name = '{client['first_name']}' where client_id = {client_id}")
        db_cursor.execute(f"update clients set last_name = '{client['last_name']}' where client_id = {client_id}")
        db_cursor.execute(f"update clients set email = '{client['email']}' where client_id = {client_id}")
        db_cursor.execute(f"update clients set password = '{client['password']}' where client_id = {client_id}")

        db_connection.commit()
    finally:
        db_connection.close()

# function to delete client by id (deletion is based on referential integrity)
def delete_client_by_id(client_id):
    logging.info('DAO: delete client')
    try:
        db_connection = get_connection()
        db_cursor = db_connection.cursor()
        db_cursor.execute(f"delete from accounts where client_id={client_id}")
        db_cursor.execute(f"delete from clients where client_id={client_id}")
        db_connection.commit()
    finally:
        db_connection.close()

# function to create new account
def create_new_account(account):
    logging.info('DAO: create new account')
    try:
        db_connection = get_connection()
        db_cursor = db_connection.cursor()
        db_cursor.execute("insert into accounts values(default, ?, ?, ?)", (account['client_id'], account['account_type'], account['balance']))
        db_connection.commit()
    finally:
        db_connection.close()

# function to get all accounts from a specific client
def get_all_accounts(client_id):
    logging.info('DAO: get all account')
    query_rows = None
    try:
        db_connection = get_connection()
        db_cursor = db_connection.cursor()
        db_cursor.execute(f"select * from accounts where client_id={client_id}")
        query_rows = db_cursor.fetchall()
    finally:
        db_connection.close()
    return query_rows

# function to get all accounts between the values of 400 to 2000 for a specific client
def get_accounts_by_range(client_id, agt, alt):
    logging.info('DAO: get accounts between amount ranges')
    query_rows = None
    try:
        db_connection = get_connection()
        db_cursor = db_connection.cursor()
        db_cursor.execute(f"select * from accounts where client_id={client_id} and balance between {agt} and {alt}")
        query_rows = db_cursor.fetchall()
    finally:
        db_connection.close()
    return query_rows

# function to get an account of a client's
def get_account_x(client_id, account_id):
    logging.info('DAO: get account')
    query_rows = None
    try:
        db_connection = get_connection()
        db_cursor = db_connection.cursor()
        db_cursor.execute(f"select * from accounts where client_id={client_id} and account_id ={account_id}")
        query_rows = db_cursor.fetchall()
    finally:
        db_connection.close()
    return query_rows

# function to update account type and/or balance
def update_account(client_id, account_id, account):
    logging.info('DAO: update account')
    try:
        db_connection = get_connection()
        db_cursor = db_connection.cursor()
        db_cursor.execute(f"update accounts set account_type = '{account['account_type']}' where client_id = {client_id} and account_id = {account_id}")
        db_cursor.execute(f"update accounts set balance = '{account['balance']}' where client_id = {client_id} and account_id = {account_id}")
        db_connection.commit()
    finally:
        db_connection.close()

# function to delete an account
def delete_account(client_id, account_id):
    logging.info('DAO: delete account')
    try:
        db_connection = get_connection()
        db_cursor = db_connection.cursor()
        db_cursor.execute(f"delete from accounts where client_id = {client_id} and account_id = {account_id}")
        db_connection.commit()
    finally:
        db_connection.close()