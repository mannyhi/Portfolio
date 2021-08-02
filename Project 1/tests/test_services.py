# Unit testing on services
from unittest import TestCase
from unittest.mock import Mock
import src.services.services as sr
import src.dao.dao as dao


class TestServices(TestCase):
    def setUp(self):
        self.mock_eid = 'cruss21'
        self.mock_epass = 'password'
        self.mock_name = 'Chris Russ'
        dao.name = Mock(return_value=[])
        dao.evp = Mock(return_value=[])
        dao.evh = Mock(return_value=[])
        dao.stats = Mock(return_value=[])
        dao.bg = Mock(return_value=[])
        dao.mvp = Mock(return_value=[])
        dao.mvh = Mock(return_value=[])

    def test_login_check(self):
        self.assertEqual(sr.login_check(self.mock_eid, self.mock_epass)[0][0], self.mock_name)
        self.assertIsInstance(sr.login_check(self.mock_eid, self.mock_epass)[0][0], str)

    def test_name(self):
        self.assertIsInstance(sr.name(self.mock_eid), dict)
        self.assertEqual(len(sr.name(self.mock_eid)), 0)

    def test_evp(self):
        self.assertIsInstance(sr.evp(self.mock_eid), dict)
        self.assertEqual(len(sr.evp(self.mock_eid)), 0)

    def test_evh(self):
        self.assertIsInstance(sr.evh(self.mock_eid), dict)
        self.assertEqual(len(sr.evh(self.mock_eid)), 0)

    def test_stats(self):
        self.assertIsInstance(sr.stats(), dict)
        self.assertEqual(len(sr.stats()), 0)

    def test_bg(self):
        self.assertIsInstance(sr.bg(), dict)
        self.assertEqual(len(sr.bg()), 0)

    def test_mvp(self):
        self.assertIsInstance(sr.mvp(), dict)
        self.assertEqual(len(sr.mvp()), 0)

    def test_mvh(self):
        self.assertIsInstance(sr.mvh(), dict)
        self.assertEqual(len(sr.mvh()), 0)
