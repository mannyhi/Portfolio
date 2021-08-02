# controller layer responsible for views and interacts with models and presentation/data
from src.models.clients import *
from src.models.accounts import *
import src.services.clients_service as cs
from src.app import flask_app
from flask import request, Response
import werkzeug.exceptions
from json import dumps

import logging
logging.basicConfig(filename='clients.log', level=logging.INFO)

# endpoint to create new client
@flask_app.route('/clients', methods=['POST'])
def create_new_client():
    client_json = request.get_json()
    logging.info(client_json)
    cs.create_new_client(client_json)
    return "Created client!", 201

# endpoint to get all clients
@flask_app.route('/clients', methods=['GET'])
def get_all_clients():
    logging.info('Requesting all clients')
    my_json = dumps(cs.get_all_clients(0), cls=UserEncoder)
    return Response(my_json), 200

# endpoint to get client by id
@flask_app.route('/clients/<int:client_id>', methods=['GET'])
def get_client_by_id(client_id):
    logging.info('The client ID that the client passed back is: ' + str(client_id))
    my_json = dumps(cs.get_all_clients(client_id), cls=UserEncoder)
    if my_json != "{}":
        return Response(my_json), 200
    else:
        return "No such client!", 404

# endpoint to update, delete or client
@flask_app.route('/clients/<int:client_id>', methods=['PUT','DELETE','POST'])
def update_client(client_id):
    client_json = request.get_json()
    logging.info('The client ID that the client passed back is: ' + str(client_id))
    if cs.udc_client(client_id, client_json, request.method):
        if request.method == 'PUT':
            return "Updated client"
        elif request.method == 'DELETE':
            return "Deleted client", 205
        elif request.method == 'POST':
            return "Created new account", 201
    else:
        return "No such client", 404

# endpoint to get accounts or all accounts
@flask_app.route('/clients/<int:client_id>/accounts', methods=['GET'])
def c7_get_all_account(client_id):
    logging.info('The client ID that the client passed back is: ' + str(client_id))
    if request.args.get("amountGreaterThan") and request.args.get("amountLessThan"):
        my_json = dumps(cs.get_accounts(client_id, request.args.get("amountGreaterThan"), request.args.get("amountLessThan"), "account ranges"), cls=UserEncoder2)
    else:
        my_json = dumps(cs.get_accounts(client_id, 0, 0, "all accounts"), cls=UserEncoder2)
    if my_json != "null" and my_json != "{}":
        return Response(my_json), 200
    else:
        return "No such account or client exists", 404

# endpoint to get account
@flask_app.route('/clients/<int:client_id>/accounts/<int:account_id>', methods=['GET'])
def get_account(client_id, account_id):
    logging.info(f"client ID: {client_id} and account ID: {account_id} passed back")
    my_json = dumps(cs.get_account_x(client_id, account_id, {}, request.method), cls=UserEncoder2)
    if my_json != "null" and my_json != "{}":
        return Response(my_json), 200
    else:
        return "No such account or client exists", 404

# endpoint to update or delete acount
@flask_app.route('/clients/<int:client_id>/accounts/<int:account_id>', methods=['PUT', 'DELETE'])
def update_account(client_id, account_id):
    account_json = request.get_json()
    logging.info(account_json)
    if cs.get_account_x(client_id, account_id, account_json, request.method):
        if request.method == 'PUT':
            return "Account updated!"
        elif request.method == 'DELETE':
            return "Account deleted", 205
    return "No such account or client exists", 404

# endpoint to withdraw or deposit a specific amount
@flask_app.route('/clients/<int:client_id>/accounts/<int:account_id>', methods=['PATCH'])
def transaction(client_id, account_id):
    amount_json = request.get_json()
    logging.info(amount_json)
    outcome = cs.transaction(client_id, account_id, amount_json)
    if outcome:
        if 422 == outcome:
            return "Insufficient funds", 422
        else:
            return "Transaction succeeded"
    else:
        return "No such account or client exists", 404

# endpoint to transfer from one account to the other
@flask_app.route('/clients/<int:client_id>/accounts/<int:account_id>/transfer/<int:account_id_2>', methods=['PATCH'])
def transfer(client_id, account_id, account_id_2):
    amount_json = request.get_json()
    outcome = cs.transfer(client_id, account_id, account_id_2, amount_json)
    if outcome:
        if 422 == outcome:
            return "insufficient funds", 422
        else:
            return f"Transfer of ${amount_json['amount']} succeeded"
    else:
        return "No such account or client exists", 404

# endpoint to handle exception when reaching an unknown resource
@flask_app.errorhandler(werkzeug.exceptions.NotFound)
def handle_404(error):
    return '404 Not Found: No such resource exists on this server', 404