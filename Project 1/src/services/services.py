# Services consist of data transformation and logic code pertaining to it
import src.dao.dao as dao
from src.models.models import *

import logging

logging.basicConfig(filename='diagnostic.log', level=logging.INFO)


# function to check credentials
def login_check(eid, epass):
    logging.info('services: login check')
    return dao.login_check(eid, epass)


# function to get name
def name(eid):
    logging.info('services: name')
    db_contents = dao.name(eid)
    content_dict = {}
    for content in db_contents:
        content_dict[content[0]] = Name(content[0])
    return content_dict


# function to create request
def create_request(eid, amount, message):
    logging.info('services: create request')
    dao.create_request(eid, amount, message)


# function for managerial approval or denial of request
def manager_update(rid, rstatus, rmessage, cname):
    logging.info('services: manager update')
    dao.manager_update(rid, rstatus, rmessage, cname)


# function to provide employee view of pending request(s)
def evp(eid):
    logging.info('services: evp')
    db_contents = dao.evp(eid)
    content_dict = {}
    for content in db_contents:
        content_dict[content[0]] = EVP(content[0], content[1], content[2], content[3], content[4])
    return content_dict


# function to provide employee view of history of request(s) made
def evh(eid):
    logging.info('services: eid')
    db_contents = dao.evh(eid)
    content_dict = {}
    for content in db_contents:
        content_dict[content[0]] = EVH(content[0], content[1], content[2], content[3], content[4], content[5],
                                       content[6], content[7])
    return content_dict


# function provide statistic information on reimbursements
def stats():
    logging.info('services: stats')
    db_contents = dao.stats()
    content_dict = {}
    for content in db_contents:
        content_dict[content[0]] = Stats(content[0], content[1], content[2], content[3], content[4])
    return content_dict


# function provide big spender information
def bg():
    logging.info('services: bg')
    db_contents = dao.bg()
    content_dict = {}
    for content in db_contents:
        content_dict['bg'] = BG(content[0], content[1])
    return content_dict


# function to provide manager view of pending request(s)
def mvp():
    logging.info('services: mvp')
    db_contents = dao.mvp()
    content_dict = {}
    for content in db_contents:
        content_dict[content[0]] = MVP(content[0], content[1], content[2], content[3], content[4], content[5],
                                       content[6])
    return content_dict


# function to provide manager view of history of request(s) made
def mvh():
    logging.info('services: mvh')
    db_contents = dao.mvh()
    content_dict = {}
    for content in db_contents:
        content_dict[content[0]] = MVH(content[0], content[1], content[2], content[3], content[4], content[5],
                                       content[6],
                                       content[7], content[8], content[9])
    return content_dict
