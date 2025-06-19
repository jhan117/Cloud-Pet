import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import reportWebVitals from "./reportWebVitals";
import { BrowserRouter } from "react-router-dom";
import { CarePlanProvider } from "./contexts/CarePlanContext";
import { TodayProvider } from "./contexts/TodayContext";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <BrowserRouter>
      <CarePlanProvider>
        <TodayProvider>
          <App />
        </TodayProvider>
      </CarePlanProvider>
    </BrowserRouter>
  </React.StrictMode>
);

reportWebVitals();
