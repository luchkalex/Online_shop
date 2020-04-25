<#include "parts/security.ftl">
<#import "parts/common.ftl" as c>

<@c.page>
    <div class="container mt-5">
        <h4 class="text-sm-center">Cart</h4>

        <#--TODO: Add form get request to order-->
        <a href="/users/order_maker">Make order</a>
        <table class="table table-striped table-hover ">
            <thead>
            <tr>
                <th class="text-center">Product</th>
                <th class="text-center">Price</th>
                <th class="text-center">Quantity</th>
                <th class="text-center">Sum</th>
                <th class="text-center">Operations</th>
            </tr>
            </thead>
            <tbody>
            <#list user.cartItems as cartItem>

                <tr>
                    <td class="text-center">${cartItem.product.title}</td>
                    <td class="text-center">${cartItem.product.price}</td>

                    <td class="text-center">
                        <form action="/users/editCart" id="editCartForm${cartItem.id?string['0']}">
                            <label for="quantity${cartItem.id}"></label>
                            <input type="text" value="${cartItem.quantity}" placeholder="Quantity" name="quantity"
                                   id="quantity${cartItem.id}" class="col-sm-5">
                            <input type="hidden" value="${cartItem.id?string['0']}" name="cartItem_id">
                        </form>
                    </td>

                    <td class="text-center">${cartItem.quantity * cartItem.product.price}</td>

                    <td class="text-center">

                        <input type="submit" class="btn btn-info" value="Save item"
                               href="#editCartForm${cartItem.id?string['0']}"
                               form="editCartForm${cartItem.id?string['0']}">

                        <a href="/users/deleteCart/${cartItem.id?string['0']}"><i class="fas fa-minus-circle"
                                                                                  style="color: red"></i></a>
                    </td>

                </tr>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>

            <#else >
                <tr>
                    <td>No products</td>
                </tr>
            </#list>
            </tbody>
        </table>

    </div>
</@c.page>