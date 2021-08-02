import unittest
from src.models.clients import Clients

class TestClients(unittest.TestCase):
    def setUp(self):
        self.client_id = 22
        self.client_first_name = 'Larry'
        self.client_last_name = 'Farmer'
        self.client_email = 'client@email.com'
        self.client_password = 'anypass'

    def tearDown(self):
        pass
