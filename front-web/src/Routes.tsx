import React from 'react';
import { BrowserRouter, Switch, Route, Redirect } from 'react-router-dom'; //instalar primeiro com o yarn, BrowserRouter este vai gerencias as nossas rotas, e o swith vai decidir qual rota deve usar
import Navbar from './core/components/Navbar';
import Admin from './pages/Admin';
import Catalog from './pages/Catalog';
import ProductDetails from './pages/Catalog/components/ProductDetails';
import Home from './pages/Home';
import Auth from './pages/Auth';
                                                //importamos tmabem o route é onde definimos de facto a url de cada rota
const Routes = () => (
        <BrowserRouter>
            <Navbar />
            <Switch>
                    <Route path="/" exact>
                        <Home />
                    </Route>
                    <Route path="/products" exact>
                        <Catalog />
                    </Route>
                    <Route path="/products/:productId">
                        <ProductDetails />
                    </Route>
                    <Redirect from="/admin/auth" to="/admin/auth/login" exact/>
                    <Route path="/admin/auth">
                        <Auth />
                    </Route>
                    <Redirect from="/admin" to="/admin/products" exact/>
                    <Route path="/admin">
                        <Admin />
                    </Route>
            </Switch>
        </BrowserRouter>

);


export default Routes;