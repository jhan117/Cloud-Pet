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
        <Link to="/">
          <h1>CloudPet</h1>
        </Link>
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
