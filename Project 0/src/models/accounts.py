from json import JSONEncoder

# accounts model
class Accounts:
    def __init__(self, account_id, client_id, account_type, balance):
        self._account_id = account_id
        self._client_id = client_id
        self._account_type = account_type
        self._balance = balance

    def get_balance(self):
        return self._balance

    def get_account_type(self):
        return self._account_type

# custom encoder type that extends this class to serialize
class UserEncoder2(JSONEncoder):
    def default(self, account):
        if isinstance(account, Accounts):
            return account.__dict__
        else:
            return super().default(self, account)