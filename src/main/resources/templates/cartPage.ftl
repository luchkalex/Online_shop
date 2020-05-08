<#include "parts/security.ftl">
<#import "parts/common.ftl" as c>

<@c.page>
    <div class="container mt-5">
        <h4 class="text-sm-center">Cart</h4>

        <a href="/users/order_maker" class="btn btn-primary">Make order</a>

        <#if cartError??>
            <div class="alert alert-danger">
                ${cartError}
            </div>
        </#if>

        <#if saveItemError??>
            <div class="mt-3 text-danger alert-danger">${saveItemError}</div>
        </#if>

        <#if quantityError??>
            <div class="alert alert-danger">
                ${quantityError}
            </div>
        </#if>

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
            <#list cartItems as cartItem>

                <tr>
                    <td class="text-center">${cartItem.product.title}</td>
                    <td class="text-center">${cartItem.product.price - cartItem.product.discount}</td>

                    <td class="text-center">
                        <form action="/users/editCart" id="editCartForm${cartItem.id?string['0']}">
                            <label for="quantity${cartItem.id}"></label>
                            <input type="number" max="1000" min="1" maxlength="4" required value="${cartItem.quantity}"
                                   placeholder="Quantity" name="quantity"
                                   id="quantity${cartItem.id}" class="col-sm-5">
                            <input type="hidden" value="${cartItem.id?string['0']}" name="cartItem_id">
                        </form>


                    </td>

                    <td class="text-center">${cartItem.quantity * (cartItem.product.price - cartItem.product.discount)}</td>

                    <td class="text-center">

                        <input type="submit" class="btn btn-info" value="Save item"
                               href="#editCartForm${cartItem.id?string['0']}"
                               form="editCartForm${cartItem.id?string['0']}">

                        <a href="/users/deleteCart/${cartItem.id?string['0']}" class="btn btn-danger"><i
                                    class="fas fa-minus-circle"></i></a>
                    </td>

                </tr>

                <input type="hidden" name="_csrf" value="${_csrf.token}"/>

            <#else >
                <tr>
                    <span>No products</span>
                </tr>
            </#list>
            </tbody>
        </table>

    </div>
</@c.page>