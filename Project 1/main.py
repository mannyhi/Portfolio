# Project 1 by Manny Hinn
from src.app import flask_app
from src.controllers import controllers

if __name__ == '__main__':
    flask_app.secret_key = 'super secret key'
    flask_app.run(debug=True)