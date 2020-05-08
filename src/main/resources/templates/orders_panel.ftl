<#import "parts/common.ftl" as c>
<#import "parts/sidebar.ftl" as s>

<@c.page>
    <@s.sidebar>
        <div class="container-fluid mw-100 ml-5">

            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <h3>Orders</h3>

            </nav>

            <div class="line"></div>

            <div>
                <form action="/control_panel/orders" method="get">
                    <table class="table table-striped table-hover ">
                        <thead>
                        <tr>
                            <th class="text-center">Id</th>
                            <th>Customer</th>
                            <th class="text-center">Total</th>
                            <th>Payment</th>
                            <th>Delivery</th>
                            <th>Address</th>
                            <th>Status</th>
                            <th class="text-center">Date</th>
                            <th>Actions</th>
                        </tr>

                        <#--FILTERS PANEL-->
                        <tr class="mw-100">
                            <td class="text-center col-sm-1">
                                <div>
                                    <input class="w-100" type="number" min="0" value="<#if of.idMin??>${of.idMin}</#if>"
                                           placeholder="min" name="idMin">
                                </div>
                                <div>
                                    <input class="w-100" type="number" max="4000000000"
                                           value="<#if of.idMax??>${of.idMax?string["0"]}</#if>" placeholder="max"
                                           name="idMax">
                                </div>
                            </td>

                            <td class="col-2">
                                <input class="w-100" type="text" maxlength="255"
                                       value="<#if of.username??>${of.username}</#if>" placeholder="Username"
                                       name="username">
                            </td>

                            <td class="text-center col-sm-1">
                                <div>
                                    <input class="w-100" type="number" min="0"
                                           value="<#if of.totalMin??>${of.totalMin}</#if>" placeholder="min"
                                           name="totalMin">
                                </div>
                                <div>
                                    <input class="w-100" type="number" max="4000000000"
                                           value="<#if of.totalMax??>${of.totalMax?string["0"]}</#if>" placeholder="max"
                                           name="totalMax">
                                </div>
                            </td>

                            <td class="col-2">
                                <select class="custom-select w-100" id="payment_select" name="payment">
                                    <option value=''>Payment</option>
                                    <#list payments as payment>
                                        <option value=${payment.id}>${payment.title}</option>
                                    </#list>
                                </select>
                            </td>

                            <td class="col-2">
                                <select class="custom-select w-100" id="delivery_select" name="delivery">
                                    <option value=''>Delivery</option>
                                    <#list deliveries as delivery>
                                        <option value=${delivery.id}>${delivery.title}</option>
                                    </#list>
                                </select>
                            </td>

                            <td class="col-2">
                                <input type="text" maxlength="255" value="${of.address}" placeholder="Address"
                                       name="address">
                            </td>

                            <td class="col-2">
                                <select class="custom-select w-100" id="statuses_select" name="status">
                                    <option value='-1'>Status</option>
                                    <#list statuses as status>
                                        <option value=${status.id}>${status.title}</option>
                                    </#list>
                                </select>
                            </td>

                            <td class="text-center col-sm-2">
                                <div>
                                    <input class="w-100" type="date" min="2000/01/01" dataformatas="yyyy/mm/dd"
                                           name="dateMin_str">
                                </div>
                                <div>
                                    <input class="w-100" type="date" dataformatas="yyyy/mm/dd" name="dateMax_str">
                                </div>
                            </td>

                            <td>
                                <input type="submit" class="btn btn-info" value="Search">
                            </td>
                        </tr>


                        <tr>
                            <#if idMinError??>
                                <div class="alert alert-danger">
                                    ${idMinError}
                                </div>
                            </#if>
                        </tr>

                        <tr>
                            <#if idMaxError??>
                                <div class="alert alert-danger">
                                    ${idMaxError}
                                </div>
                            </#if>
                        </tr>

                        <tr>
                            <#if totalMinError??>
                                <div class="alert alert-danger">
                                    ${totalMinError}
                                </div>
                            </#if>
                        </tr>

                        <tr>
                            <#if totalMaxError??>
                                <div class="alert alert-danger">
                                    ${totalMaxError}
                                </div>
                            </#if>
                        </tr>

                        <tr>
                            <#if usernameError??>
                                <div class="alert alert-danger">
                                    ${usernameError}
                                </div>
                            </#if>
                        </tr>

                        <tr>
                            <#if addressError??>
                                <div class="alert alert-danger">
                                    ${addressError}
                                </div>
                            </#if>
                        </tr>


                        </thead>
                        <tbody>
                        <#list orders as order>

                            <tr>
                                <td class="text-center">${order.id}</td>

                                <td>
                                    <#if order.user??>${order.user.username}<#else >Unknown</#if><br>
                                    <#if order.customerEmail?has_content>Email: ${order.customerEmail}
                                        <br><#elseif order.user??>Email: ${order.user.email}<br></#if>
                                    <#if order.user?? && order.user.phone?has_content>Phone: ${order.user.phone}</#if>
                                </td>

                                <td class="text-center">${order.total}</td>

                                <td>${order.typeOfPayment.title}</td>

                                <td>${order.typesOfDelivery.title}</td>

                                <td>${order.address}</td>

                                <td>${order.orderStatuses.title!'No status'}</td>

                                <td class="text-center">${order.orderDate!'No date'}</td>
                                <td>
                                    <a href="/users/order/${order.id}" type="submit"
                                       class="btn btn-primary btn-middle-size">
                                        <i class="fas fa-eye"></i>
                                    </a>
                                    <#if order.orderStatuses.title = "Awaiting confirmation">
                                        <a href="/users/Accepted/${order.id}" type="submit"
                                           class="btn btn-info btn-middle-size mt-2"><i class="fas fa-check-circle"></i></a>

                                        <a href="/users/Rejected/${order.id}" type="submit"
                                           class="btn btn-danger btn-middle-size mt-2"><i
                                                    class="fas fa-times-circle"></i></a>
                                    </#if>

                                    <#if order.orderStatuses.title = "Accepted">
                                        <a href="/users/Completed/${order.id}" type="submit"
                                           class="btn btn-warning btn-middle-size mt-2">Complete</a>
                                    </#if>
                                </td>
                            </tr>
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>

                        <#else >
                            <tr>
                                <td>No orders</td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                </form>
            </div>
        </div>
    </@s.sidebar>
</@c.page>