// eslint-disable-next-line no-unused-vars
import React from 'react';
import { Form, Input, Button, message } from 'antd';
import { useNavigate } from 'react-router-dom';
import './CreateBounty.css';
import {createBounty} from "../../services/api.js";

const CreateBounty = () => {
    const [form] = Form.useForm();
    const navigate = useNavigate();

    const handleSubmit = async (values) => {
        try {
            await createBounty(values);
            message.success('悬赏令创建成功');
            navigate('/bounties');
        } catch (error) {
            message.error('悬赏令创建失败，请重试');
        }
    };

    return (
        <div className="create-bounty-container">
            <h2>创建悬赏令</h2>
            <Form form={form} onFinish={handleSubmit}>
                <Form.Item label="标题" name="title" rules={[{ required: true, message: '请输入标题' }]}>
                    <Input />
                </Form.Item>
                <Form.Item label="描述" name="description" rules={[{ required: true, message: '请输入描述' }]}>
                    <Input.TextArea />
                </Form.Item>
                <Form.Item label="赏金" name="reward" rules={[{ required: true, message: '请输入赏金' }]}>
                    <Input />
                </Form.Item>
                <Form.Item>
                    <Button type="primary" htmlType="submit">创建</Button>
                </Form.Item>
            </Form>
        </div>
    );
};

export default CreateBounty;
