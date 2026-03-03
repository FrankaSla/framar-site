(() => {
  const lb = document.getElementById("lightbox");
  if (!lb) return;

  const stage = document.getElementById("lbStage");
  const thumbs = document.getElementById("lbThumbs");
  const backdrop = document.getElementById("lbBackdrop");

  const btnClose = document.getElementById("lbClose");
  const btnPrev = document.getElementById("lbPrev");
  const btnNext = document.getElementById("lbNext");

  // Skupljamo sve thumbnail slike iz galerije
  // BITNO: u galeriji slike moraju imati klasu .gallery-item i unutar <img>
  const items = Array.from(document.querySelectorAll(".gallery-item img"));

  let index = 0;

  function openAt(i){
    if (!items.length) return;
    index = (i + items.length) % items.length;

    lb.classList.add("open");
    lb.setAttribute("aria-hidden", "false");

    // ubaci sliku u stage
    stage.innerHTML = "";
    const img = document.createElement("img");
    img.className = "lb-media";
    img.src = items[index].src;
    img.alt = "Galerija";
    stage.appendChild(img);

    // thumbs (opcionalno)
    thumbs.innerHTML = "";
    items.forEach((it, idx) => {
      const t = document.createElement("button");
      t.type = "button";
      t.className = "lb-thumb" + (idx === index ? " active" : "");
      const ti = document.createElement("img");
      ti.src = it.src;
      ti.alt = "Thumb";
      t.appendChild(ti);
      t.addEventListener("click", () => openAt(idx));
      thumbs.appendChild(t);
    });
  }

  function close(){
    lb.classList.remove("open");
    lb.setAttribute("aria-hidden", "true");
    stage.innerHTML = "";
  }

  function prev(){ openAt(index - 1); }
  function next(){ openAt(index + 1); }

  // Klik na galeriju otvara lightbox
  items.forEach((img, i) => {
    img.addEventListener("click", (e) => {
      e.preventDefault();
      openAt(i);
    });
  });

  // Kontrole
  btnClose?.addEventListener("click", close);
  backdrop?.addEventListener("click", close);
  btnPrev?.addEventListener("click", (e) => { e.stopPropagation(); prev(); });
  btnNext?.addEventListener("click", (e) => { e.stopPropagation(); next(); });

  // ESC + strelice na tipkovnici
  document.addEventListener("keydown", (e) => {
    if (!lb.classList.contains("open")) return;
    if (e.key === "Escape") close();
    if (e.key === "ArrowLeft") prev();
    if (e.key === "ArrowRight") next();
  });
})();