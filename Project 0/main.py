#Project 0 by Manny Hinn
from src.app import flask_app
from src.controllers import client_controller

@flask_app.route('/')
def BankingAPI():
    return 'Banking API'

if __name__=='__main__':
    flask_app.run(debug=True)


