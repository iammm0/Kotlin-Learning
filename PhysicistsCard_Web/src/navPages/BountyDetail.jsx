// eslint-disable-next-line no-unused-vars
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getBountyDetail } from '../services/api';
import { Card, Spin } from 'antd';
import './BountyDetail.css';

const BountyDetail = () => {
    const { id } = useParams();
    const [bounty, setBounty] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchBounty = async () => {
            try {
                const data = await getBountyDetail(id);
                setBounty(data);
                setLoading(false);
            } catch (error) {
                console.error('Failed to fetch bounty', error);
                setLoading(false);
            }
        };

        fetchBounty();
    }, [id]);

    if (loading) {
        return <Spin size="large" />;
    }

    if (!bounty) {
        return <div>悬赏令未找到</div>;
    }

    return (
        <div className="bounty-detail-container">
            <Card title={bounty.title}>
                <p>{bounty.description}</p>
                <p>赏金: {bounty.description}</p>
            </Card>
        </div>
    );
};

export default BountyDetail;
