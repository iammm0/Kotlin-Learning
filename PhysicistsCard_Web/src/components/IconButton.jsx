// IconButton.jsx
// eslint-disable-next-line no-unused-vars
import React from 'react';

// eslint-disable-next-line react/prop-types
const IconButton = ({ iconClass, iconSrc, onClick, style, alt }) => {
    return (
        <button onClick={onClick} style={{ ...styles.button, ...style }}>
            {iconSrc ? (
                <img src={iconSrc} alt={alt} style={styles.iconImage} />
            ) : (
                <i className={iconClass} alt={alt}></i>
            )}
        </button>
    );
};

const styles = {
    button: {
        backgroundColor: 'transparent',
        border: 'none',
        cursor: 'pointer',
        padding: '10px',
        margin: '0 5px',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
    },
    iconImage: {
        width: '24px', // 调整图标大小
        height: '24px',
    },
};

export default IconButton;
