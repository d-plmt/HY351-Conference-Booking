import logo from './logo.svg';
import './styles/App.css';
import {Routes, Route, Link} from "react-router-dom";
import { Header } from './Header';
import { LoginPage } from './LoginPage';
import { HomePage } from './HomePage';

function App() {
  return (
    <div class="root">
      <Link to=".." relative="/" style={{ textDecoration: 'none', color: 'black'}}>
        <Header/>
      </Link>
        
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/login" element={<LoginPage />} />
      </Routes>
    </div>

  );
}

export default App;
