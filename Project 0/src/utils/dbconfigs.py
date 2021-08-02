# standard configuration to establish connection to database
from pyodbc import connect
import os

db_url = os.environ['db_url']
db_name = os.environ['db_name']
db_username = os.environ['db_username']
db_password = os.environ['db_password']

def get_connection():
    return connect(f"DRIVER={{PostgreSQL UNICODE}};SERVER={db_url};PORT=5432;DATABASE={db_name};UID={db_username};PWD={db_password};Trusted_Connection=no")
