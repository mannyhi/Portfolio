# step definition file: the source "glue" code associated feature file.
from behave import *


@given('User is on ERS home page')
def test_on_home_page(context):
    context.driver.get('http://localhost:5000/ers.html')


@when('User enters the wrong credentials')
def test_user_enters_wrong_credentials(context):
    context.sll_page.enter_credentials()


@then('User is redirected to the ERS home page')
def test_user_redirected_ERS(context):
    assert 'ers' in context.driver.current_url


@when('User enters the correct credentials')
def test_user_enters_credentials(context):
    context.sll_page.enter_credentials(1)


@when('User clicks the submit button')
def test_user_clicks_submit(context):
    context.sll_page.click_login_button()


@when('User hits the enter button to login')
def test_user_hits_enter_to_login(context):
    context.sll_page.hit_enter_button()


@then('User is redirected to the employee dashboard page')
def test_user_redirected_employee_db(context):
    assert 'erse' in context.driver.current_url


@when('User submit first request on employee dashboard page')
def test_user_clicks_submit(context):
    context.sll_page.create_request()


@when('User submit second request on employee dashboard page')
def test_user_clicks_submit_second_request(context):
    context.sll_page.create_request(1)


@when('User clicks the log out button on employee dashboard page')
def test_logout(context):
    context.sll_page.logout()


@then('User is redirected to the ERS homepage')
def test_user_redirected_ERS(context):
    assert 'ers' in context.driver.current_url


@when('User hits the enter button to post request')
def test_user_hits_enter_button_to_post_request(context):
    context.sll_page.hit_enter_button(1)


@when('User enters the correct manager credentials')
def test_user_enters_credentials(context):
    context.sll_page.enter_credentials(2)


@then('User is redirected to the manager dashboard page')
def test_user_redirected_manager(context):
    assert 'ersm' in context.driver.current_url


@when('User approves first request on manager dashboard page')
def test_manager_action(context):
    context.sll_page.manager_action()


@when('User denies second request on manager dashboard page')
def test_user_manager_action_2(context):
    context.sll_page.manager_action(1)


@when('User clicks the log out button on manager dashboard page')
def test_logout(context):
    context.sll_page.logout()
