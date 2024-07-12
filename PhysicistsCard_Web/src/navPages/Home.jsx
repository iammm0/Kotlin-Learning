// eslint-disable-next-line no-unused-vars
import React from 'react';
import './Home.css';
import appIcon from '../assets/PhysCard.png';
import iosIcon from '../assets/ios.png';
import androidIcon from '../assets/android.png';

const Home = () => (
    <div>
        <div className="appInfo">
            <img src={appIcon} alt="App Icon" className="appIcon"/>
            <h1 className="appName">PHYSICISTS <br/>CARD</h1>
        </div>
        <div className="downloadButtons">
            <a href="https://example.com/ios-download" className="downloadButton">
                <img src={iosIcon} alt="iOS" className="downloadIcon"/>
                <span>iOS Download</span>
            </a>
            <a href="https://example.com/android-download" className="downloadButton">
                <img src={androidIcon} alt="Android" className="downloadIcon"/>
                <span>Android Download</span>
            </a>
        </div>
    </div>

);

export default Home;
