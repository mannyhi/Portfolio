# Accessing DB data and executing SQL statements
# DB data has two tables: Employees and Reimbursements having a 1:M relationship

# import to establish database connection
from src.utils.dbconfigs import get_connection

import logging

logging.basicConfig(filename='diagnostic.log', level=logging.INFO)


# function to check credentials within DB
def login_check(eid, epass):
    logging.info('dao: login check')
    try:
        db_connection = get_connection()
        db_cursor = db_connection.cursor()
        db_cursor.execute(f"select ename from employees where eid='{eid}' and epassword = '{epass}'")
        query_rows = db_cursor.fetchall()
    finally:
        db_connection.close()
    return query_rows


# function to check credentials within DB
def name(eid):
    logging.info('dao: name')
    try:
        db_connection = get_connection()
        db_cursor = db_connection.cursor()
        db_cursor.execute(f"select ename from employees where eid='{eid}'")
        query_rows = db_cursor.fetchall()
    finally:
        db_connection.close()
    return query_rows


# function to create request in DB
def create_request(eid, amount, message):
    logging.info('dao: create request')
    try:
        db_connection = get_connection()
        db_cursor = db_connection.cursor()
        db_cursor.execute("insert into reimbursements values(default, ?, ?, ?, 'pending', '')", (eid, amount, message))
        db_connection.commit()
    finally:
        db_connection.close()


# function for managerial approval or denial of request in DB
def manager_update(rid, rstatus, rmessage, cname):
    logging.info('dao: manager update')
    try:
        db_connection = get_connection()
        db_cursor = db_connection.cursor()
        db_cursor.execute(
            f"update reimbursements set ctime = now(), rstatus = '{rstatus}', rmessage ='{rmessage}', cname ='{cname[0][0]}' where rid = {rid}")
        db_connection.commit()
    finally:
        db_connection.close()


# function to provide employee view of pending request(s) from DB
def evp(eid):
    logging.info('dao: evp')
    try:
        db_connection = get_connection()
        db_cursor = db_connection.cursor()
        db_cursor.execute(
            f"select LPAD((rid::text), 4, '0'), ramount, reason, rtime, rstatus from reimbursements where rstatus = 'pending' and eid = '{eid}' order by rid")
        epending = db_cursor.fetchall()
    finally:
        db_connection.close()
    return epending


# function to provide employee view of history of request(s) made from DB
def evh(eid):
    logging.info('dao: evh')
    try:
        db_connection = get_connection()
        db_cursor = db_connection.cursor()
        db_cursor.execute(
            f"select LPAD((rid::text), 4, '0'), ramount, reason, rtime, rstatus, rmessage, ctime, cname from reimbursements where rstatus <> 'pending' and eid = '{eid}' order by rid")
        ehistory = db_cursor.fetchall()
    finally:
        db_connection.close()
    return ehistory


# function provide statistic information on reimbursements from DB
def stats():
    logging.info('dao: stats')
    try:
        db_connection = get_connection()
        db_cursor = db_connection.cursor()
        db_cursor.execute(
            "select count(rid) as total,  sum(case rstatus when 'approved' then 1 else 0 end) as approved, sum(case rstatus when 'denied' then 1 else 0 end) as denied, sum(case rstatus when 'pending' then 1 else 0 end) as pending, money(avg(case rstatus when 'approved' then (ramount::numeric)  else null end)) as mean from reimbursements")
        statsinfo = db_cursor.fetchall()
    finally:
        db_connection.close()
    return statsinfo


# function provide big spender information from DB
def bg():
    logging.info('dao: bg')
    try:
        db_connection = get_connection()
        db_cursor = db_connection.cursor()
        db_cursor.execute(
            """select employees.ename, sum(ramount) from reimbursements join employees on reimbursements.eid = employees.eid
where reimbursements.rstatus = 'approved' group by  reimbursements.eid, employees.eid order by sum(ramount) desc limit 1""")
        bigspender = db_cursor.fetchall()
    finally:
        db_connection.close()
    return bigspender


# function to provide manager view of pending request(s) from DB
def mvp():
    logging.info('dao: mvp')
    try:
        db_connection = get_connection()
        db_cursor = db_connection.cursor()
        db_cursor.execute("""select LPAD((rid::text), 4, '0'), employees.ename, employees.eid, ramount, reason, rtime, rstatus from reimbursements join employees on reimbursements.eid = employees.eid
where reimbursements.rstatus = 'pending' group by reimbursements.eid, employees.eid, reimbursements.rid order by rid""")
        query_rows = db_cursor.fetchall()
    finally:
        db_connection.close()
    return query_rows


# function to provide manager view of history of request(s) made from DB
def mvh():
    logging.info('dao: mvh')
    try:
        db_connection = get_connection()
        db_cursor = db_connection.cursor()
        db_cursor.execute("""select LPAD((rid::text), 4, '0'), employees.ename, employees.eid, ramount, reason, rtime, rstatus, rmessage, ctime, cname from reimbursements join employees on reimbursements.eid = employees.eid
where reimbursements.rstatus <> 'pending' group by reimbursements.eid, employees.eid, reimbursements.rid order by rid""")
        query_rows = db_cursor.fetchall()
    finally:
        db_connection.close()
    return query_rows
