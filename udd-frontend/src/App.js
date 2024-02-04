import { BrowserRouter as Router, Route, Routes, useNavigate } from 'react-router-dom';
import { v4 as uuidv4 } from 'uuid';
import axios from 'axios';
import { useEffect } from 'react';
//import './App.css';

import Navbar from './components/Navbar';
import Search from './components/Search';
import Registration from './components/Registration';
import AdvancedSearch from './components/AdvancedSearch';

axios.defaults.baseURL = "http://localhost:8080/";

function App() {

  useEffect(() => {
    document.title = "UDD"
  }, [])

  return (
    <div className="App">
      <Router>
      <Routes>
          <Route key={uuidv4()} exact path="/" element={[<Navbar key={uuidv4()}/>, <Search key={uuidv4()}/>]}/>
          <Route key={uuidv4()} exact path="/combinedSearch" element={[<Navbar key={uuidv4()}/>, <AdvancedSearch key={uuidv4()}/>]}/>
          <Route key={uuidv4()} exact path="/registration" element={[<Navbar key={uuidv4()}/>, <Registration key={uuidv4()}/>]}/>
          </Routes>
        </Router>
    </div>
  );
}

export default App;
