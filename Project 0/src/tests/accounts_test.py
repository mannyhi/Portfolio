import unittest
from src.models.accounts import Accounts

class TestAccount(unittest.TestCase):
    def setUp(self):
        self.account_type = "checking"
        self.balance = 100

    def tearDown(self):
        pass

    def test_get_balance(self):
        self.assertEqual(self.balance, 100)

    def test_get_account_type(self):
        self.assertEqual(self.account_type, 'checking')

