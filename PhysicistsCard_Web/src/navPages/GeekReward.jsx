// eslint-disable-next-line no-unused-vars
import React, { useEffect, useState } from 'react';
import { getBounties } from '../services/api';
import { useNavigate } from 'react-router-dom';
import { List, Card, Button } from 'antd';
import './GeekReward.css';

const GeekReward = () => {
    const [bounties, setBounties] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchBounties = async () => {
            try {
                const data = await getBounties();
                setBounties(data);
            } catch (error) {
                console.error('Failed to fetch bounties', error);
            }
        };

        fetchBounties();
    }, []);

    const handleCreateBounty = () => {
        navigate('/create-bounty');
    };

    return (
        <div className="geek-reward-container">
            <h1>悬赏列表</h1>
            <Button type="primary" onClick={handleCreateBounty} style={{ marginBottom: '20px' }}>
                创建悬赏令
            </Button>
            <List
                grid={{ gutter: 16, column: 1 }}
                dataSource={bounties}
                renderItem={(bounty) => (
                    <List.Item>
                        <Card title={bounty.title}>
                            <p>{bounty.description}</p>
                            <Button type="link" onClick={() => navigate(`/bounties/${bounty.id}`)}>
                                查看详情
                            </Button>
                        </Card>
                    </List.Item>
                )}
            />
        </div>
    );
};

export default GeekReward;
