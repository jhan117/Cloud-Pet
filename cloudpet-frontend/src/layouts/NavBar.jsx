import { Link } from "react-router-dom";

import classes from "./NavBar.module.css";

const NavBar = ({ open, onClose }) => {
  return (
    <>
      <aside className={`${classes.navBar} ${open && classes.open}`}>
        <ul className={classes.navBarList}>
          <li>
            <Link to="/">Home</Link>
          </li>
          <li>
            <Link to="/plans">Plans</Link>
          </li>
          <li>
            <Link to="/history">History</Link>
          </li>
          <li>
            <Link to="/settings">Settings</Link>
          </li>
        </ul>
      </aside>
      <div
        className={`${classes.overlay} ${!open && classes.overlayHidden}`}
        onClick={onClose}
      />
    </>
  );
};

export default NavBar;
