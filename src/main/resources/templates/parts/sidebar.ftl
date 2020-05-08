<#macro sidebar>
    <div class="wrapper sidebar">
        <nav id="sidebar">

            <div class="sidebar-header">
                <h3>CONTROL PANEL</h3>
            </div>

            <ul class="list-unstyled components">
                <li>
                    <a href="/control_panel/products">Products</a>
                </li>

                <li>
                    <a href="/control_panel/categories">Categories</a>
                </li>

                <li>
                    <a href="/control_panel/features">Features</a>
                </li>

                <li>
                    <a href="/control_panel/orders">Orders</a>
                </li>

                <li>
                    <a href="/control_panel/sales_stat">Sales statistic</a>
                </li>
            </ul>
        </nav>

        <#nested>
    </div>
</#macro>