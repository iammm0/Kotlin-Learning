// eslint-disable-next-line no-unused-vars
import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Button } from 'antd';
import './UserProfile.css';

const UserProfile = () => {
    const navigate = useNavigate();

    const handleRegister = () => {
        navigate('/register');
    };

    const handleLogin = () => {
        navigate('/login');
    };

    return (
        <div className="user-profile-container">
            <h2>欢迎来到极客悬赏</h2>
            <p>请注册或登录以继续。</p>
            <div className="button-container">
                <Button type="primary" onClick={handleRegister}>
                    注册
                </Button>
                <Button onClick={handleLogin}>
                    登录
                </Button>
            </div>
        </div>
    );
};

export default UserProfile;
