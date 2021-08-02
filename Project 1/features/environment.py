from features.poms.ers_repo import ERS
from selenium import webdriver
import time


def before_all(context):
    context.driver = webdriver.Chrome()
    context.sll_page = ERS(context.driver)


def after_step(context, step):
    time.sleep(2)
    pass


def after_all(context):
    context.driver.quit()
