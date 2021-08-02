# Unit testing on DAO
from unittest import TestCase
from unittest.mock import patch
from pyodbc import connect
import src.dao.dao as dao


class TestDao(TestCase):
    def setUp(self):
        self.conn = connect(
            "DRIVER=PostgreSQL UNICODE(x64);SERVER=localhost;PORT=5432;DATABASE=postgres;UID=postgres;Trusted_Connection=no")

    @patch('src.dao.dao.get_connection')
    def test_login_check(self, mock_conn):
        mock_conn.return_value = self.conn
        mock_cur = self.conn.cursor()
        mock_eid = 'kchilds77'
        mock_epass = 'password'
        mock_cur.execute(f"select ename from employees where eid='{mock_eid}' and epassword = '{mock_epass}'")
        expected = mock_cur.fetchall()
        actual = dao.login_check(mock_eid, mock_epass)
        self.assertEqual(expected, actual)

    @patch('src.dao.dao.get_connection')
    def test_name(self, mock_conn):
        mock_conn.return_value = self.conn
        mock_cur = self.conn.cursor()
        mock_eid = 'kchilds77'
        mock_cur.execute(f"select ename from employees where eid='{mock_eid}'")
        expected = mock_cur.fetchall()
        actual = dao.name(mock_eid)
        self.assertEqual(expected, actual)

    @patch('src.dao.dao.get_connection')
    def test_create_request(self, mock_conn):
        mock_conn.return_value = self.conn
        mock_cur = self.conn.cursor()
        mock_eid = 'kchilds77'
        mock_amount = '13'
        mock_message = 'unittest'
        mock_cur.execute("insert into reimbursements values(default, ?, ?, ?, 'pending', '')",
                         (mock_eid, mock_amount, mock_message))
        mock_conn.commit()

    @patch('src.dao.dao.get_connection')
    def test_evp(self, mock_conn):
        mock_conn.return_value = self.conn
        mock_cur = self.conn.cursor()
        mock_eid = 'kchilds77'
        mock_cur.execute(
            f"select LPAD((rid::text), 4, '0'), ramount, reason, rtime, rstatus from reimbursements where rstatus = 'pending' and eid = '{mock_eid}' order by rid")
        expected = mock_cur.fetchall()
        actual = dao.evp(mock_eid)
        self.assertEqual(expected, actual)

    @patch('src.dao.dao.get_connection')
    def test_evh(self, mock_conn):
        mock_conn.return_value = self.conn
        mock_cur = self.conn.cursor()
        mock_eid = 'kchilds77'
        mock_cur.execute(
            f"select LPAD((rid::text), 4, '0'), ramount, reason, rtime, rstatus, rmessage, ctime, cname from reimbursements where rstatus <> 'pending' and eid = '{mock_eid}' order by rid")
        expected = mock_cur.fetchall()
        actual = dao.evh(mock_eid)
        self.assertEqual(expected, actual)

    @patch('src.dao.dao.get_connection')
    def test_stats(self, mock_conn):
        mock_conn.return_value = self.conn
        mock_cur = self.conn.cursor()
        mock_cur.execute(
            "select count(rid) as total,  sum(case rstatus when 'approved' then 1 else 0 end) as approved, sum(case rstatus when 'denied' then 1 else 0 end) as denied, sum(case rstatus when 'pending' then 1 else 0 end) as pending, money(avg(case rstatus when 'approved' then (ramount::numeric)  else null end)) as mean from reimbursements")
        expected = mock_cur.fetchall()
        actual = dao.stats()
        self.assertEqual(expected, actual)

    @patch('src.dao.dao.get_connection')
    def test_bg(self, mock_conn):
        mock_conn.return_value = self.conn
        mock_cur = self.conn.cursor()
        mock_cur.execute(
            """select employees.ename, sum(ramount) from reimbursements join employees on reimbursements.eid = employees.eid
where reimbursements.rstatus = 'approved' group by  reimbursements.eid, employees.eid order by sum(ramount) desc limit 1""")
        expected = mock_cur.fetchall()
        actual = dao.bg()
        self.assertEqual(expected, actual)

    @patch('src.dao.dao.get_connection')
    def test_mvp(self, mock_conn):
        mock_conn.return_value = self.conn
        mock_cur = self.conn.cursor()
        mock_cur.execute("""select LPAD((rid::text), 4, '0'), employees.ename, employees.eid, ramount, reason, rtime, rstatus from reimbursements join employees on reimbursements.eid = employees.eid
where reimbursements.rstatus = 'pending' group by reimbursements.eid, employees.eid, reimbursements.rid order by rid""")
        expected = mock_cur.fetchall()
        actual = dao.mvp()
        self.assertEqual(expected, actual)

    @patch('src.dao.dao.get_connection')
    def test_mvh(self, mock_conn):
        mock_conn.return_value = self.conn
        mock_cur = self.conn.cursor()
        mock_cur.execute("""select LPAD((rid::text), 4, '0'), employees.ename, employees.eid, ramount, reason, rtime, rstatus, rmessage, ctime, cname from reimbursements join employees on reimbursements.eid = employees.eid
where reimbursements.rstatus <> 'pending' group by reimbursements.eid, employees.eid, reimbursements.rid order by rid""")
        expected = mock_cur.fetchall()
        actual = dao.mvh()
        self.assertEqual(expected, actual)
