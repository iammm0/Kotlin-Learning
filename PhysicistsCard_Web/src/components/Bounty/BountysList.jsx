// eslint-disable-next-line no-unused-vars
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { List, Card, Button } from 'antd';
import './BountyList.css';
import { getBounties } from "../../services/api";

const BountyList = () => {
    const [bounties, setBounties] = useState([]);

    useEffect(() => {
        const fetchBounties = async () => {
            const data = await getBounties();
            console.log(data); // 打印数据以检查其结构
            setBounties(data);
        };

        fetchBounties();
    }, []);

    return (
        <div className="bounty-list-container">
            <h2>悬赏令列表</h2>
            <Link to="/create-bounty">
                <Button type="primary" style={{ marginBottom: '20px' }}>创建悬赏令</Button>
            </Link>
            <List
                grid={{ gutter: 16, column: 1 }}
                dataSource={bounties}
                renderItem={bounty => (
                    <List.Item key={bounty.id}>
                        <Card title={bounty.title}>
                            <p>{bounty.description}</p>
                            <Link to={`/bounties/${bounty.id}`}>查看详情</Link>
                        </Card>
                    </List.Item>
                )}
            />
        </div>
    );
};

export default BountyList;
