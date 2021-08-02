# General Flask configuration.
from flask import Flask

flask_app = Flask(__name__, static_url_path='')


@flask_app.route('/')  # Defined route to application root: http://localhost:5000/.
def project1():
    return 'Project 1'
