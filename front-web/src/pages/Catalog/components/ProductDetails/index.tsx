import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import './styles.scss';
import {ReactComponent as ArrowIcon} from '../../../../core/assets/images/arrow.svg'
import {ReactComponent as ProductImage} from '../../../../core/assets/images/product.svg' 
import ProductPrice from '../../../../core/components/ProductPrice';
import { makeRequest } from '../../../../core/utils/request';
import { Product } from '../../../../core/types/Product';

type ParamsType = {
    productId: string;
}
//row por defeito ja e display flex depois dentro dessa div crio outras duas para dividir a tela em 2 colunas, sendo que o 
//bootsrap tem 12 colunas basta dar col-6 para cada dv
//bg-primary classes que o bootstrap ja tem, bg-secondary igual isto se quiser usar em alguma coisa, text-center tambem é
//pr-5 tambem é e quer dizer padding-right tamnho 5

const ProductDetails= () => {

    const {productId}  = useParams<ParamsType>();

    //console.log(productId);

    const [product, setProduct] = useState<Product>();

    useEffect(()=> {
        makeRequest({url: `/products/${productId}`})
        .then(response => setProduct(response.data)) //depois de fazer a linha acima eu quero popular um estado criado acima
    }, [productId]);//todas as variaveis que usamos no useeffect temos de o deixar aqui como dependencia tambem

    return(
        <div className="product-details-container">
            <div className="card-base border-radius-20 product-details">
                <Link to='/products' className="products-details-goback">
                    <ArrowIcon className='icon-goback' />
                    <h1 className="text-goback">voltar</h1>
                </Link>
                <div className="row"> 
                    <div className="col-6 pr-5">
                        <div className="product-details-card text-center">
                            <ProductImage className="products-detail-image" />
                        </div>
                        <h1 className="product-details-name">
                            {product?.name}
                        </h1>
                        {product?.price && <ProductPrice price={product?.price}/>}
                    </div>
                    <div className="col-6 product-details-card">
                        <h1 className="product-descrition-title">
                            Descrição do Produto
                        </h1>
                        <p className="product-description-text">
                            Seja um mestre em multitarefas com a capacidade para exibir quatro 
                            aplicativos simultâneos na tela. A tela está ficando abarrotada? 
                            Crie áreas de trabalho virtuais para obter mais espaço e trabalhar 
                            com os itens que você deseja. Além disso, todas as notificações e 
                            principais configurações são reunidas em uma única tela de fácil acesso.
                        </p>
                    </div>
                </div>

            </div>

        </div>
    );
};




export default ProductDetails;

