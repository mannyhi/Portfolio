from selenium.webdriver.common.keys import Keys
import os


class ERS:
    employee_id = os.environ['employee_id']
    employee_pw = os.environ['employee_pw']
    manager_id = os.environ['manager_id']
    manager_pw = os.environ['manager_pw']

    def __init__(self, driver):
        self.driver = driver

    def enter_credentials(self, credential=0):
        if (credential == 0):
            self.driver.find_element_by_id('username').send_keys('wrong user')
            self.driver.find_element_by_id('password').send_keys('wrong pw')
        elif (credential == 1):
            self.driver.find_element_by_id('username').send_keys(self.employee_id)
            self.driver.find_element_by_id('password').send_keys(self.employee_pw)
        elif (credential == 2):
            self.driver.find_element_by_id('username').send_keys(self.manager_id)
            self.driver.find_element_by_id('password').send_keys(self.manager_pw)

    def click_login_button(self):
        self.driver.find_element_by_id('submit').click()

    def hit_enter_button(self, request=0):
        if (request):
            self.driver.find_element_by_id('reason').send_keys(Keys.ENTER)
        else:
            self.driver.find_element_by_id('password').send_keys(Keys.ENTER)

    def create_request(self, second=0):
        if (second):
            self.driver.find_element_by_id('amount').send_keys('2.00')
            self.driver.find_element_by_id('reason').send_keys('2nd automated test')
        else:
            self.driver.find_element_by_id('amount').send_keys('1.00')
            self.driver.find_element_by_id('reason').send_keys('1st automated test')

    def logout(self):
        self.driver.find_element_by_id('logout').send_keys(Keys.ENTER)

    def manager_action(self, deny=0):
        if (deny):
            self.driver.find_element_by_id('message1').send_keys('2nd automated step to deny')
            self.driver.find_element_by_id('deny1').send_keys(Keys.ENTER)
        else:
            self.driver.find_element_by_id('message1').send_keys('1st automated step to approve')
            self.driver.find_element_by_id('approve1').send_keys(Keys.ENTER)
