// eslint-disable-next-line no-unused-vars
import React, {useState} from 'react';
import {BrowserRouter as Router, Route, Routes, Link, useNavigate} from 'react-router-dom';
import './App.css';
import userIcon from './assets/user.svg';
import userShoppingIcon from './assets/shopping.svg';
import amateurIcon from './assets/amateur.svg';
import specialistIcon from './assets/specialist.svg';
import growForeverIcon from './assets/growForever.svg';
import geekRewardIcon from './assets/geekReward.svg';
import supportIcon from './assets/support.svg';
import Home from './navPages/Home.jsx';
import SpecialistCommunity from "./navPages/SpecialistCommunity.jsx";
import AmateurCommunity from "./navPages/AmateurCommunity.jsx";
import GeekReward from "./navPages/GeekReward.jsx";
import GrowForever from "./navPages/GrowForever.jsx";
import IconButton from "./components/IconButton.jsx";
import SearchBar from "./components/SearchBar.jsx";
import UserProfile from "./navPages/UserProfile.jsx";
import Supports from "./navPages/Supports.jsx";
import CardBags from "./navPages/CardBags.jsx";
import Login from "./components/auth/Login.jsx";
import Register from "./components/auth/Register.jsx";
import BountyDetail from "./navPages/BountyDetail.jsx";
import CreateBounty from "./components/Bounty/CreateBounty.jsx";
import BountyList from "./components/Bounty/BountysList.jsx";

const App = () => {

    const [isLoggedIn, setIsLoggedIn] = useState(false);

    const handleLogin = () => {
        setIsLoggedIn(true);
    };

    const handleRegister = () => {
        setIsLoggedIn(true);
    };

    return (
        <Router>
            <div className="container">
                <header className="header">
                    <div className="iconButton">
                        <i className="fas fa-home"></i>
                    </div>
                    <nav className="nav">
                        <Link to="/specialist" className="navItem">
                            <img src={ specialistIcon } alt="specialist" className="navIcon" />
                            专业者社区
                        </Link>
                        <Link to="/amateur" className="navItem">
                            <img src={ amateurIcon } alt="amateur" className="navIcon" />
                            爱好者社区
                        </Link>
                        <Link to="/geekreward" className="navItem">
                            <img src={ geekRewardIcon } alt="geekreward" className="navIcon" />
                            极客悬赏
                        </Link>
                        <Link to="/growforever" className="navItem">
                            <img src={ growForeverIcon } alt="growforever" className="navIcon" />
                            永恒之森
                        </Link>
                    </nav>
                    <div className="iconButtons">
                        <SearchBar />
                    </div>
                    <div className="iconButtons">
                        <IconButtonsWithNavigation />
                    </div>
                </header>

                <main className="main">
                    <Routes>
                        <Route path="/user-profile" element={<UserProfile />} />
                        <Route path="/register" element={<Register onClose={handleRegister}/>} />
                        <Route path="/login" element={<Login onClose={handleLogin}/>} />
                        <Route path="/" element={<Home />} />
                        <Route path="/geekreward" element={<GeekReward />} />
                        <Route path="/bounties/:id" element={<BountyDetail />} />
                        <Route path="/specialist" element={<SpecialistCommunity />} />
                        <Route path="/amateur" element={<AmateurCommunity />} />
                        <Route path="/geekreward" element={<GeekReward />} />
                        <Route path="/growforever" element={<GrowForever />} />
                        <Route path="/home" element={<Home />} />
                        <Route path="/supports" element={<Supports />} />
                        <Route path="/card-bags" element={<CardBags />} />
                        <Route path="/bounties" element={<BountyList />} />
                        <Route path="/bounties/:id" element={<BountyDetail />} />
                        <Route path="/create-bounty" element={<CreateBounty />} />
                    </Routes>
                </main>

                <footer className="footer">
                    <p>&copy; 2024 双煎滑蛋有限公司 All rights reserved.</p>
                </footer>

            </div>
        </Router>
    );
};

const IconButtonsWithNavigation = () => {
    const navigate = useNavigate();

    const handleIconClick = (path) => {
        navigate(path);
    };

    return (
        <div className="iconButtons">
            <IconButton iconSrc={userIcon} onClick={() => handleIconClick('/user-profile')} alt="User" />
            <IconButton iconSrc={userShoppingIcon} onClick={() => handleIconClick('/card-bags')} alt="Cart" />
            <IconButton iconSrc={supportIcon} onClick={() => handleIconClick('/supports')} alt="Support" />
        </div>
    );
};

export default App;
