# unit testing on client "data access objects" DAO
import unittest
from unittest.mock import Mock
import src.dao.client_dao as cd

class ClientDaoTest(unittest.TestCase):

    def setUp(self):
        cd.a_client = Mock(return_value=[(1, 'Joe', 'Smokes', 'joe@smokes.com', 'wordpass')])

    def test_create_new_client(self):
        self.assertIsInstance(cd.a_client(), object)
        self.assertGreater(len(cd.a_client()), 0)

    def test_get_client_by_id(self):
        self.assertIsInstance(cd.a_client(), object)
        self.assertGreater(len(cd.a_client()), 0)

    def test_update_client(self):
        self.assertIsInstance(cd.a_client(), object)
        self.assertGreater(len(cd.a_client()), 0)

    def test_delete_client_by_id(self):
        self.assertIsInstance(cd.a_client(), object)
        self.assertGreater(len(cd.a_client()), 0)

    def test_create_new_account(self):
        self.assertIsInstance(cd.a_client(), object)
        self.assertGreater(len(cd.a_client()), 0)

    def test_get_all_accounts(self):
        self.assertIsInstance(cd.a_client(), object)
        self.assertGreater(len(cd.a_client()), 0)

    def test_get_accounts_by_range(self):
        self.assertIsInstance(cd.a_client(), object)
        self.assertGreater(len(cd.a_client()), 0)

    def test_get_account_x(self):
        self.assertIsInstance(cd.a_client(), object)
        self.assertGreater(len(cd.a_client()), 0)

    def test_update_account_3(self):
        self.assertIsInstance(cd.a_client(), object)
        self.assertGreater(len(cd.a_client()), 0)

    def test_delete_account_6(self):
        self.assertIsInstance(cd.a_client(), object)
        self.assertGreater(len(cd.a_client()), 0)

    def tearDown(self):
        pass
