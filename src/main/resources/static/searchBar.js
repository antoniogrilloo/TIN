document.onreadystatechange = function () {
    if (document.readyState == "complete") {
        function filterItems() {
            var searchValue = document.getElementById('searchInput').value.toLowerCase();
            var categoryValue = document.getElementById('categorySelect').value;
            var listItems = document.querySelectorAll('#list li');

        }

        function toggleCategorySelect() {
            var categorySelect = document.getElementById('categorySelect');
            if (categorySelect.classList.contains('hidden')) {
                categorySelect.classList.remove('hidden');
            } else {
                categorySelect.classList.add('hidden');
            }
        }

        document.querySelector('.filter-icon').addEventListener('click', function() {
            toggleCategorySelect();
        });
    }
}