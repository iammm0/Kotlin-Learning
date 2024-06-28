// SearchBar.jsx
// eslint-disable-next-line no-unused-vars
import React, { useState } from 'react';

// eslint-disable-next-line react/prop-types
const SearchBar = ({ placeholder, onSearch }) => {
    const [searchTerm, setSearchTerm] = useState('');

    const handleChange = (event) => {
        setSearchTerm(event.target.value);
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        onSearch(searchTerm);
    };

    return (
        <form onSubmit={handleSubmit} style={styles.form}>
            <input
                type="text"
                value={searchTerm}
                onChange={handleChange}
                placeholder={placeholder}
                style={styles.input}
            />
            <button type="submit" style={styles.button}>
                浏览
            </button>
        </form>
    );
};

const styles = {
    form: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        marginBottom: '2px',
    },
    input: {
        padding: '10px',
        fontSize: '14px',
        borderRadius: '10px',
        border: '1px solid #ccc',
        marginRight: '20px',
    },
    button: {
        width: '100%',
        padding: '10px 20px',
        fontSize: '12px',
        borderRadius: '5px',
        border: 'none',
        backgroundColor: '#6a4b94',
        color: 'white',
        cursor: 'pointer',
    },
};

export default SearchBar;
