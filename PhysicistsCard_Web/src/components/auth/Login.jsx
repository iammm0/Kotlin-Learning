// eslint-disable-next-line no-unused-vars
import React from 'react';
import { Form, Input, Button, message } from 'antd';
import { useNavigate } from 'react-router-dom';
import { loginUser } from '../../services/api';
import "./Login.css"

// eslint-disable-next-line react/prop-types
const Login = ({onClose }) => {
    const [form] = Form.useForm();
    const navigate = useNavigate();

    const handleSubmit = async (values) => {
        try {
            const response = await loginUser(values);
            localStorage.setItem('token', response.token);
            message.success('登录成功');
            form.resetFields();  // 重置表单
            onClose();  // 关闭登录窗口
            navigate('/home');  // 导航到主页
        } catch (error) {
            if (error.response && error.response.data && error.response.data.message) {
                message.error(error.response.data.message); // 显示后端返回的具体错误信息
            } else {
                message.error('登录失败，请重试');
            }
        }
    };

    return (
        <div className="modal">
            <div className="modal-content">
                <span className="close" onClick={onClose}>&times;</span>
                <h2>登录</h2>
                <Form form={form} onFinish={handleSubmit}>
                    <Form.Item label="邮箱" name="email" rules={[{ required: true, message: '请输入邮箱地址' }]}>
                        <Input />
                    </Form.Item>
                    <Form.Item label="密码" name="password" rules={[{ required: true, message: '请输入密码' }]}>
                        <Input.Password />
                    </Form.Item>
                    <Form.Item>
                        <Button type="primary" htmlType="submit">登录</Button>
                    </Form.Item>
                </Form>
            </div>
        </div>
    );
};

export default Login;
