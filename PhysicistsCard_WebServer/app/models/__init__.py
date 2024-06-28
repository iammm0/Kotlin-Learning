from flask_login import UserMixin


class User(UserMixin):
    def __init__(self, id, username, email, password_hash):
        self.id = id
        self.username = username
        self.email = email
        self.password_hash = password_hash

    def get_id(self):
        return str(self.id)

    @staticmethod
    def from_mongo(doc):
        return User(
            id=str(doc['_id']),
            username=doc['username'],
            email=doc['email'],
            password_hash=doc['password']
        )
