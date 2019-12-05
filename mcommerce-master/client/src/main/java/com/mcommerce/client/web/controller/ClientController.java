package com.mcommerce.client.web.controller;

import com.mcommerce.client.beans.ProductBean;
import com.mcommerce.client.proxies.MicroserviceProduitsProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
public class ClientController {

    @Autowired
    private MicroserviceProduitsProxy produitsProxy;

    @RequestMapping("/")
    public String accueil(Model model){

       List<ProductBean> produits = produitsProxy.listeDesProduits();

       model.addAttribute("produits", produits);

        return "Accueil";
    }

    @GetMapping("/details-produit/{id}")
    public String showProduit(@PathVariable int id, Model model) {

        ProductBean produit = produitsProxy.recupererUnProduit(id);

        model.addAttribute("produit", produit);

        return "showProduit";
    }

}