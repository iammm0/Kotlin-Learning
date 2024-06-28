from flask import Blueprint, request, jsonify
from app import mongo, bcrypt
from flask_login import login_user, logout_user, current_user

from app.models import User

api_bp = Blueprint('api', __name__)


@api_bp.route('/register', methods=['POST'])
def register():
    data = request.get_json()
    username = data.get('username')
    email = data.get('email')
    password = data.get('password')

    if not username or not email or not password:
        return jsonify({'error': 'Missing fields'}), 400

    if mongo.db.users.find_one({'email': email}):
        return jsonify({'error': 'Email already exists'}), 400

    hashed_password = bcrypt.generate_password_hash(password).decode('utf-8')
    mongo.db.users.insert_one({'username': username, 'email': email, 'password': hashed_password})

    return jsonify({'message': 'User registered successfully'}), 201


@api_bp.route('/login', methods=['POST'])
def login():
    data = request.get_json()
    email = data.get('email')
    password = data.get('password')

    if not email or not password:
        return jsonify({'error': 'Missing fields'}), 400

    user = mongo.db.users.find_one({'email': email})

    if user and bcrypt.check_password_hash(user['password'], password):
        user_obj = User.from_mongo(user)
        login_user(user_obj)
        return jsonify({'message': 'Login successful'}), 200

    return jsonify({'error': 'Invalid credentials'}), 401


@api_bp.route('/logout', methods=['POST'])
def logout():
    logout_user()
    return jsonify({'message': 'Logged out successfully'}), 200


@api_bp.route('/user', methods=['GET'])
def get_user():
    if current_user.is_authenticated:
        user_data = {
            'username': current_user.username,
            'email': current_user.email
        }
        return jsonify(user_data), 200
    return jsonify({'error': 'Unauthorized'}), 401
