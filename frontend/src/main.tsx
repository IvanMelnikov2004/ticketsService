import {StrictMode} from 'react'
import {createRoot} from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import {BrowserRouter, Route, Routes} from "react-router";
import {SignIn} from "./SignIn.tsx";
import {ProtectedRoute} from "./ProtectedRoute.tsx";
import {CustomFlowbiteTheme, Flowbite} from "flowbite-react";
import {AnonymousRoute} from "./AnonymousRoute.tsx";
import Cities from "./Cities.tsx";
import RoutePage from "./RoutePage.tsx";
import Register from "./Register.tsx";
import Tickets from "./Tickets.tsx";

const ticketsServiceTheme: CustomFlowbiteTheme = {
    textarea: {
        colors: {
            gray: "dark:bg-gray-900 dark:text-white",
        }
    }
}

createRoot(document.getElementById('root')!).render(
  <StrictMode>
      <Flowbite theme={{ theme: ptmplaceTheme }}>
          <BrowserRouter>
              <Routes>
                  <Route path="/" element={<ProtectedRoute>
                      <App />
                  </ProtectedRoute>} />
                  <Route path="/cities" element={<ProtectedRoute>
                      <Cities />
                  </ProtectedRoute>} />
                  <Route path="/tickets" element={<ProtectedRoute>
                      <Tickets />
                  </ProtectedRoute>} />
                  <Route path="/route/:from/:to/:mode/:departure" element={<ProtectedRoute>
                      <RoutePage />
                  </ProtectedRoute>} />
                  <Route path="/signin" element={<AnonymousRoute>
                      <SignIn />
                  </AnonymousRoute>} />
                  <Route path="/register" element={<AnonymousRoute>
                      <Register />
                  </AnonymousRoute>} />
              </Routes>
          </BrowserRouter>
      </Flowbite>
  </StrictMode>,
)
