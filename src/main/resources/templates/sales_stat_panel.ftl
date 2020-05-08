<#import "parts/common.ftl" as c>
<#import "parts/sidebar.ftl" as s>

<@c.page>
    <@s.sidebar>
        <div class="container-fluid ml-5">
            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <h3>Sales statistic</h3>

            </nav>

            <div class="line"></div>

            <div>
                <table class="table table-striped table-hover ">
                    <thead>
                    <tr>
                        <th class="text-center">Date</th>
                        <th class="text-center">Sold items</th>
                        <th class="text-center">Order placed</th>
                        <th class="text-center">Revenue</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list sales_stat as stat_item>

                        <tr>
                        <td class="text-center">${stat_item.date}</td>
                        <td class="text-center">${stat_item.sold}</td>
                        <td class="text-center">${stat_item.orderPlaced}</td>
                        <td class="text-center">${stat_item.revenue}</td>
                    <#else >
                        <tr>
                            <td>No data</td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>
    </@s.sidebar>
</@c.page>