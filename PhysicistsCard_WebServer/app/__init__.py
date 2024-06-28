import os

from flask import Flask
from flask_wtf import CSRFProtect
from flask_login import LoginManager
from flask_bcrypt import Bcrypt
from flask_cors import CORS
from flask_pymongo import PyMongo

mongo = PyMongo()
csrf = CSRFProtect()
login_manager = LoginManager()
bcrypt = Bcrypt()
cors = CORS()


def create_app(config_class='config.Config'):
    template_dir = os.path.abspath(os.path.join(os.path.dirname(__file__), '..', 'templates'))
    static_dir = os.path.abspath(os.path.join(os.path.dirname(__file__), '..', 'static'))
    app = Flask(__name__, template_folder=template_dir, static_folder=static_dir)
    app.config.from_object(config_class)

    csrf.init_app(app)
    login_manager.init_app(app)
    bcrypt.init_app(app)
    cors.init_app(app)
    mongo.init_app(app)

    from app.models import User

    @login_manager.user_loader
    def load_user(user_id):
        user_doc = mongo.db.users.find_one({'_id': user_id})
        return User.from_mongo(user_doc) if user_doc else None

    register_blueprints(app)

    return app


def register_blueprints(app):
    from app.routes.main import main_bp
    from app.routes.api import api_bp
    app.register_blueprint(main_bp)
    app.register_blueprint(api_bp, url_prefix='/api')
