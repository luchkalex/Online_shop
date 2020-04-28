<#import "parts/common.ftl" as c>
<#import "parts/sidebar.ftl" as s>

<@c.page>
    <@s.sidebar>
        <div class="container-fluid ml-5">

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
                        <tr>
                            <td class="text-center">
                                <div>
                                    <input type="text" value="${of.idMin}" placeholder="min" name="idMin"
                                           class="col-sm-5">
                                </div>
                                <div>
                                    <input type="text" value="${of.idMax?string["0"]}" placeholder="max" name="idMax"
                                           class="col-sm-5">
                                </div>
                            </td>

                            <td>
                                <input type="text" value="${of.username}" placeholder="Username" name="username"
                                       class="">
                            </td>

                            <td class="text-center">
                                <div>
                                    <input type="text" value="${of.totalMin}" placeholder="min" name="totalMin"
                                           class="col-sm-5">
                                </div>
                                <div>
                                    <input type="text" value="${of.totalMax?string["0"]}" placeholder="max"
                                           name="totalMax"
                                           class="col-sm-5">
                                </div>
                            </td>

                            <td>
                                <select class="custom-select" id="payment_select" name="payment">
                                    <option value=''>Payment</option>
                                    <#list payments as payment>
                                        <option value=${payment.id}>${payment.title}</option>
                                    </#list>
                                </select>
                            </td>

                            <td>
                                <select class="custom-select" id="delivery_select" name="delivery">
                                    <option value=''>Delivery</option>
                                    <#list deliveries as delivery>
                                        <option value=${delivery.id}>${delivery.title}</option>
                                    </#list>
                                </select>
                            </td>

                            <td>
                                <input type="text" value="${of.address}" placeholder="Address" name="address">
                            </td>

                            <td>
                                <select class="custom-select" id="statuses_select" name="status">
                                    <option value='-1'>Status</option>
                                    <#list statuses as status>
                                        <option value=${status.id}>${status.title}</option>
                                    </#list>
                                </select>
                            </td>

                            <td class="text-center">
                                <div>
                                    <input type="date" dataformatas="yyyy/mm/dd" name="dateMin_str"
                                           class="col-sm-5">
                                </div>
                                <div>
                                    <input type="date" dataformatas="yyyy/mm/dd" name="dateMax_str"
                                           class="col-sm-5">
                                </div>
                            </td>

                            <td>
                                <input type="submit" class="btn btn-info" value="Search">
                            </td>
                        </tr>

                        </thead>
                        <tbody>
                        <#list orders as order>

                            <tr>
                                <td class="text-center">${order.id}</td>

                                <td><#if order.user??>${order.user.username}<#else >Unknown</#if></td>

                                <td class="text-center">${order.total}</td>

                                <td>${order.typeOfPayment.title}</td>

                                <td>${order.typesOfDelivery.title}</td>

                                <td>${order.address}</td>

                                <td>${order.orderStatuses.title!'No status'}</td>

                                <td class="text-center">${order.orderDate!'No date'}</td>
                                <td>
                                    <a href="/users/order/${order.id}" type="submit"
                                       class="btn btn-primary btn-middle-size">View</a>
                                    <#if order.orderStatuses.title = "Awaiting confirmation">
                                        <a href="/users/Accepted/${order.id}" type="submit"
                                           class="btn btn-info btn-middle-size mt-2">Accept</a>

                                        <a href="/users/Rejected/${order.id}" type="submit"
                                           class="btn btn-danger btn-middle-size mt-2">Reject</a>
                                    </#if>

                                    <#if order.orderStatuses.title = "Accepted" || order.orderStatuses.title = "Rejected">
                                        <a href="/users/Completed/${order.id}" type="submit"
                                           class="btn btn-info btn-middle-size mt-2">Complete</a>
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