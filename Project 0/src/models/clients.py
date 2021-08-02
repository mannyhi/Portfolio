from json import JSONEncoder

# clients model
class Clients:
    def __init__(self, client_id, client_first_name, client_last_name, client_email, client_password):
        self._client_id = client_id
        self._client_first_name = client_first_name
        self._client_last_name = client_last_name
        self._client_email = client_email
        self._client_password = client_password

# custom encoder type that extends this class to serialize
class UserEncoder(JSONEncoder):
    def default(self, client):
        if isinstance(client, Clients):
            return client.__dict__
        else:
            return super().default(self, client)