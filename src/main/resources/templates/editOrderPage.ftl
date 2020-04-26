<#import "parts/common.ftl" as c>

<@c.page>

    <div class="form-group container">
        <form method="post">
            <div class="col-sm-4 mt-2 p-0">
                <label for="payment_select" class="mt-2">Select type of payment</label>
                <select class="custom-select" id="payment_select" name="payment_id">
                    <option value='-1'>Chose type of payment</option>
                    <#list types_of_payment as type_of_payment>
                        <option value=${type_of_payment.id}>${type_of_payment.title}</option>
                    </#list>
                </select>
            </div>

            <div class="col-sm-4 mt-2 p-0">
                <label for="delivery_select" class="mt-2">Select type of delivery</label>
                <select class="custom-select" id="delivery_select" name="delivery_id">
                    <option value='-1'>Chose type of delivery</option>
                    <#list types_of_delivery as type_of_delivery>
                        <option value=${type_of_delivery.id}>${type_of_delivery.title}</option>
                    </#list>
                </select>
            </div>

            <#--TODO: Add value of address from user-->

            <div id="address" class="mt-3">
                <label for="address_inp">Address</label>
                <input type="text" id="address_inp" class="form-control-sm col-sm-5" name="address"
                       value="<#if user.address??>${user.address}</#if>">
            </div>

            <table class="table table-striped table-hover ">
                <thead>
                <tr>
                    <th class="text-center">Product</th>
                    <th class="text-center">Quantity</th>
                    <th class="text-center">Sum</th>
                </tr>
                </thead>
                <tbody>
                <#assign sum = 0>
                <#list user.cartItems as cartItem>
                    <tr>
                        <td class="text-center">${cartItem.product.title}</td>
                        <td class="text-center">${cartItem.quantity}</td>
                        <td class="text-center">${cartItem.quantity * cartItem.product.price}</td>
                        <#assign sum = sum + cartItem.quantity * cartItem.product.price>
                    </tr>
                <#else >
                    <tr>
                        <td>No products</td>
                    </tr>
                </#list>
                <tr class="bg-success">
                    <td class="text-center">Total sum</td>
                    <td></td>
                    <td class="text-center">${sum}</td>
                </tr>
                </tbody>
            </table>


            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-primary my-1">Make order</button>
        </form>
    </div>
</@c.page>