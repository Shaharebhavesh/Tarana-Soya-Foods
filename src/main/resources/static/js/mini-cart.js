document.addEventListener("DOMContentLoaded", function () {

  const toggle = document.getElementById("miniCartToggle");
  const miniCart = document.getElementById("miniCart");

// Prevent dropdown from closing on + / − / remove clicks
miniCart.addEventListener("click", function (e) {
  e.stopPropagation();
});


  if (!toggle || !miniCart) return;

  // Load cart items
  function loadMiniCart() {
    fetch("/cart/mini")
      .then(res => res.json())
      .then(items => {
        miniCart.innerHTML = "";

        if (!items || items.length === 0) {
          miniCart.innerHTML =
            `<li class="text-center text-muted small">
              Your cart is empty
            </li>`;
          updateCartCount();
          return;
        }

        let total = 0;

        items.forEach(item => {
          total += item.total;

          miniCart.innerHTML += `
            <li class="mb-2">
              <strong>${item.name}</strong>

              <div class="d-flex align-items-center justify-content-between mt-1">
                <div class="btn-group btn-group-sm">
                  <button class="btn btn-outline-success"
                          onclick="changeQty(${item.productId}, ${item.quantity - 1})">−</button>

                  <span class="px-2">${item.quantity}</span>

                  <button class="btn btn-outline-success"
                          onclick="changeQty(${item.productId}, ${item.quantity + 1})">+</button>
                </div>

                <button class="btn btn-sm btn-danger"
                        onclick="removeItem(${item.productId})">
                  ✕
                </button>
              </div>

              <small class="text-muted">
                ₹${item.price} each
              </small>
            </li>
          `;
        });

        miniCart.innerHTML += `
          <li><hr class="dropdown-divider"></li>
          <li class="text-end fw-bold">Total: ₹${total}</li>
          <li class="mt-2">
            <a href="/cart" class="btn btn-sm btn-success w-100">
              View Cart
            </a>
          </li>
        `;

        updateCartCount();
      });
  }

  // Load on click
  toggle.addEventListener("click", loadMiniCart);

  // Auto close when clicking outside
  document.addEventListener("click", function (e) {
    if (!toggle.contains(e.target) && !miniCart.contains(e.target)) {
      bootstrap.Dropdown.getOrCreateInstance(toggle).hide();
    }
  });

  // Expose functions globally
  window.changeQty = function (productId, qty) {
    fetch("/cart/update", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: `productId=${productId}&quantity=${qty}`
    }).then(loadMiniCart);
  };

  window.removeItem = function (productId) {
    fetch("/cart/remove", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: `productId=${productId}`
    }).then(loadMiniCart);
  };

});
function updateCartCount() {
  fetch("/cart/count")
    .then(res => res.json())
    .then(count => {
      const badge = document.getElementById("cartCount");
      if (!badge) return;

      if (count > 0) {
        badge.innerText = count;
        badge.style.display = "inline-block";

        // bounce animation
        badge.classList.remove("cart-bounce");
        void badge.offsetWidth;
        badge.classList.add("cart-bounce");

      } else {
        badge.innerText = "0";
        badge.style.display = "none";
      }
    });
}
