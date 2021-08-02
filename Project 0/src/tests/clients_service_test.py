# unit testing on client services
import unittest
from unittest.mock import Mock
import src.services.clients_service as cs

class ClientServiceTest(unittest.TestCase):

    def setUp(self):
        cs.a_client = Mock(return_value=[(1, 'Charlize', 'Bowman', 'charlize@mail.com', 'sassy5')])

    def test_create_new_client(self):
        self.assertIsInstance(cs.a_client(), object)
        self.assertGreater(len(cs.a_client()), 0)

    def test_get_all_clients(self):
        self.assertIsInstance(cs.a_client(), object)
        self.assertGreater(len(cs.a_client()), 0)

    def test_udc_client(self):
        self.assertIsInstance(cs.a_client(), object)
        self.assertGreater(len(cs.a_client()), 0)

    def test_get_accounts(self):
        self.assertIsInstance(cs.a_client(), object)
        self.assertGreater(len(cs.a_client()), 0)

    def test_get_account_x(self):
        self.assertIsInstance(cs.a_client(), object)
        self.assertGreater(len(cs.a_client()), 0)

    def test_transaction(self):
        self.assertIsInstance(cs.a_client(), object)
        self.assertGreater(len(cs.a_client()), 0)

    def test_transfer(self):
        self.assertIsInstance(cs.a_client(), object)
        self.assertGreater(len(cs.a_client()), 0)

    def tearDown(self):
        pass