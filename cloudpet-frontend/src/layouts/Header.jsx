import { useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBars, faTimes } from "@fortawesome/free-solid-svg-icons";
import NavBar from "./NavBar";
import { Link } from "react-router-dom";

import classes from "./Header.module.css";

const Header = () => {
  const [navBarOpen, setNavBarOpen] = useState(false);

  return (
    <>
      <header
        className={`${classes.header} ${
          navBarOpen && classes.headerHighlighted
        }`}
      >
        <div className={classes.titleCon}>
          <Link to="/">
            <h1>CloudPet</h1>
          </Link>
          <p>
            컴퓨터공학과
            <br />
            20240058 권기영
          </p>
        </div>
        <button
          className={classes.menuButton}
          open={navBarOpen}
          onClick={() => setNavBarOpen((prev) => !prev)}
        >
          {navBarOpen ? (
            <FontAwesomeIcon icon={faTimes} />
          ) : (
            <FontAwesomeIcon icon={faBars} />
          )}
        </button>
      </header>
      <NavBar open={navBarOpen} onClose={() => setNavBarOpen(false)} />
    </>
  );
};

export default Header;
