// eslint-disable-next-line no-unused-vars
import React from 'react';
import { Form, Input, Button, message } from 'antd';
import { useNavigate } from 'react-router-dom';
import { registerUser } from '../../services/api';
import "./Register.css"

// eslint-disable-next-line react/prop-types
const Register = ({ onClose }) => {
    const [form] = Form.useForm();
    const navigate = useNavigate();

    const handleSubmit = async (values) => {
        try {
            await registerUser(values);
            message.success('注册成功');
            onClose();  // 关闭注册窗口
            form.resetFields();  // 重置表单
            navigate('/login');  // 导航到登录页面
        } catch (error) {
            if (error.response && error.response.data && error.response.data.message) {
                message.error(error.response.data.message); // 显示后端返回的具体错误信息
            } else {
                message.error('注册失败，请重试');
            }
        }
    };

    return (
        <div className="modal">
            <div className="modal-content">
                <span className="close" onClick={onClose}>&times;</span>
                <h2>注册</h2>
                <Form form={form} onFinish={handleSubmit}>
                    <Form.Item label="用户名" name="username" rules={[{ required: true, message: '请输入用户名' }]}>
                        <Input />
                    </Form.Item>
                    <Form.Item label="邮箱" name="email" rules={[{ required: true, message: '请输入邮箱' }, { type: 'email', message: '请输入有效的邮箱地址' }]}>
                        <Input />
                    </Form.Item>
                    <Form.Item label="密码" name="password" rules={[{ required: true, message: '请输入密码' }, { min: 6, message: '密码至少6个字符' }]}>
                        <Input.Password />
                    </Form.Item>
                    <Form.Item>
                        <Button type="primary" htmlType="submit">注册</Button>
                    </Form.Item>
                </Form>
            </div>
        </div>
    );
};

export default Register;
