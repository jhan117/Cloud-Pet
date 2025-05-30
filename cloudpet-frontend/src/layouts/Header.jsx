import { useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBars, faTimes } from "@fortawesome/free-solid-svg-icons";
import classes from "./Header.module.css";
import NavBar from "./NavBar";

const Header = () => {
  const [navBarOpen, setNavBarOpen] = useState(false);

  return (
    <>
      <header
        className={`${classes.header} ${
          navBarOpen && classes.headerHighlighted
        }`}
      >
        <h1>CloudPet</h1>
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
