import { useState } from "react";
import "./App.css";

const initialChecks = [
  { name: "Java environment", detail: "JDK 21 detected", category: "Environment", status: "Passed", icon: "☕" },
  { name: "Maven configuration", detail: "Maven 3.9.15 detected", category: "Build", status: "Passed", icon: "⌘" },
  { name: "Compilation", detail: "Source compilation check", category: "Build", status: "Passed", icon: "✓" },
  { name: "Automated tests", detail: "JUnit test suite", category: "Tests", status: "Passed", icon: "▤" },
  { name: "Dependencies", detail: "Dependency review required", category: "Dependencies", status: "Warning", icon: "◇" },
  { name: "Git working tree", detail: "Repository status", category: "Git", status: "Passed", icon: "⑂" },
  { name: "Security configuration", detail: "Configuration review pending", category: "Security", status: "Warning", icon: "⬡" },
  { name: "JAR packaging", detail: "Runnable artifact verification", category: "Packaging", status: "Pending", icon: "□" },
];

const navigation = [
  { heading: "WORKSPACE", items: ["Overview", "Project Checks", "Build & Tests", "Dependencies"] },
  { heading: "DELIVERY", items: ["Git & Release", "Security", "Reports"] },
];

const pageDescriptions = {
  Overview: "Your project's release health at a glance.",
  "Project Checks": "Review individual release-readiness checks.",
  "Build & Tests": "Inspect compilation and automated test readiness.",
  Dependencies: "Review dependency configuration and warnings.",
  "Git & Release": "Review repository readiness and release metadata.",
  Security: "Review security-related checks.",
  Reports: "Review your release-readiness summary.",
  Settings: "Manage your JavaShip workspace.",
};

function StatusBadge({ status }) {
  const className = status.toLowerCase().replace(" ", "-");
  return <span className={`status-badge ${className}`}><span className="status-dot" />{status}</span>;
}

function MetricCard({ icon, label, value, note, tone }) {
  return (
    <article className="metric-card">
      <div className={`metric-icon ${tone}`}>{icon}</div>
      <p className="metric-label">{label}</p>
      <h2 className="metric-value">{value}</h2>
      <p className="metric-note">{note}</p>
    </article>
  );
}

function App() {
  const [activeNav, setActiveNav] = useState("Overview");
  const [checks, setChecks] = useState(initialChecks);
  const [query, setQuery] = useState("");
  const [running, setRunning] = useState(false);
  const [lastRun, setLastRun] = useState("Not run in this session");
  const [profileOpen, setProfileOpen] = useState(false);
  const [notificationsOpen, setNotificationsOpen] = useState(false);
  const [sidebarOpen, setSidebarOpen] = useState(false);

  const passed = checks.filter((item) => item.status === "Passed").length;
  const warnings = checks.filter((item) => item.status === "Warning").length;
  const failed = checks.filter((item) => item.status === "Failed").length;
  const pending = checks.filter((item) => item.status === "Pending").length;
  const readiness = Math.round((passed / checks.length) * 100);

  const filteredChecks = checks.filter((item) =>
    `${item.name} ${item.detail} ${item.category} ${item.status}`
      .toLowerCase()
      .includes(query.toLowerCase())
  );

  function runChecks() {
    if (running) return;
    setRunning(true);
    setLastRun("Demo check running...");

    // This is a frontend demonstration, not a real Java build.
    window.setTimeout(() => {
      setChecks((current) =>
        current.map((item) =>
          item.status === "Pending" ? { ...item, status: "Warning" } : item
        )
      );
      setLastRun(`Completed at ${new Date().toLocaleTimeString([], {
        hour: "2-digit",
        minute: "2-digit",
      })}`);
      setRunning(false);
    }, 900);
  }

  function navigate(name) {
    setActiveNav(name);
    setSidebarOpen(false);
  }

  return (
    <div className="app-shell">
      {sidebarOpen && (
        <button className="mobile-scrim" aria-label="Close menu" onClick={() => setSidebarOpen(false)} />
      )}

      <aside className={`sidebar ${sidebarOpen ? "sidebar-open" : ""}`}>
        <div className="brand">
          <div className="brand-mark">⌘</div>
          <div>
            <div className="brand-name">JavaShip<span>.</span></div>
            <div className="brand-subtitle">DEVELOPER PLATFORM</div>
          </div>
          <button className="icon-button sidebar-close" onClick={() => setSidebarOpen(false)}>×</button>
        </div>

        <div className="workspace-switch">
          <div className="workspace-avatar">J</div>
          <div className="workspace-copy">
            <strong>JavaShip</strong>
            <span>Personal workspace</span>
          </div>
          <span className="muted">⌄</span>
        </div>

        <nav className="navigation">
          {navigation.map((group) => (
            <div className="nav-group" key={group.heading}>
              <p className="nav-label">{group.heading}</p>
              {group.items.map((name) => (
                <button
                  key={name}
                  className={`nav-item ${activeNav === name ? "active" : ""}`}
                  onClick={() => navigate(name)}
                >
                  <span className="nav-symbol">
                    {name === "Overview" ? "◫" :
                     name === "Project Checks" ? "✓" :
                     name === "Build & Tests" ? "⌘" :
                     name === "Dependencies" ? "◇" :
                     name === "Git & Release" ? "⑂" :
                     name === "Security" ? "⬡" : "▤"}
                  </span>
                  <span>{name}</span>
                  {name === "Dependencies" && warnings > 0 && <span className="nav-count">{warnings}</span>}
                </button>
              ))}
            </div>
          ))}
        </nav>

        <div className="sidebar-bottom">
          <div className="help-card">
            <div className="help-icon">?</div>
            <strong>Need a hand?</strong>
            <p>Explore your release workflow.</p>
            <button onClick={() => navigate("Reports")}>View reports <span>→</span></button>
          </div>
          <button className={`nav-item ${activeNav === "Settings" ? "active" : ""}`} onClick={() => navigate("Settings")}>
            <span className="nav-symbol">⚙</span><span>Settings</span>
          </button>
          <div className="sidebar-footer"><span className="online-dot" /> All systems operational <span>v1.0.0</span></div>
        </div>
      </aside>

      <main className="main-area">
        <header className="topbar">
          <div className="breadcrumb">
            <button className="icon-button mobile-menu" onClick={() => setSidebarOpen(true)}>☰</button>
            <span>Workspace</span><span className="muted">/</span><strong>{activeNav}</strong>
          </div>

          <div className="topbar-actions">
            <label className="global-search">
              <span>⌕</span>
              <input value={query} onChange={(event) => setQuery(event.target.value)} placeholder="Search checks..." />
              <kbd>⌘ K</kbd>
            </label>

            <div className="popover-wrap">
              <button className="icon-button notification-button" onClick={() => setNotificationsOpen(!notificationsOpen)} aria-label="Notifications">
                ♧{warnings > 0 && <span className="notification-dot" />}
              </button>
              {notificationsOpen && (
                <div className="floating-panel">
                  <strong>Notifications</strong>
                  <p className="panel-description">{warnings + failed} items need review.</p>
                  {checks.filter((item) => item.status === "Warning" || item.status === "Failed").map((item) => (
                    <div className="notification-item" key={item.name}>
                      <span className="warning-symbol">!</span>
                      <div><strong>{item.name}</strong><small>{item.detail}</small></div>
                    </div>
                  ))}
                </div>
              )}
            </div>

            <div className="profile-wrap">
              <button className="profile-button" onClick={() => setProfileOpen(!profileOpen)}>
                <span className="profile-avatar">SM</span>
                <span className="profile-text"><strong>Shreya Mumbale</strong><small>Developer</small></span>
                <span className="muted">⌄</span>
              </button>
              {profileOpen && (
                <div className="floating-panel profile-menu">
                  <small>WORKSPACE PROFILE</small>
                  <strong>Shreya Mumbale</strong>
                  <span className="panel-description">JavaShip developer</span>
                  <button onClick={() => { navigate("Settings"); setProfileOpen(false); }}>⚙ Workspace settings</button>
                  <button onClick={() => { navigate("Reports"); setProfileOpen(false); }}>▤ View reports</button>
                </div>
              )}
            </div>
          </div>
        </header>

        <div className="page-content">
          <section className="welcome-row">
            <div>
              <div className="eyebrow"><span /> YOUR DEVELOPMENT WORKSPACE</div>
              <h1>{activeNav === "Overview" ? <>Good afternoon, <span>Shreya.</span></> : activeNav}</h1>
              <p className="page-description">{pageDescriptions[activeNav]}</p>
            </div>
            <div className="welcome-actions">
              <span className="branch-chip">⑂ &nbsp; main</span>
              <button className="primary-button" onClick={runChecks} disabled={running}>
                <span className={running ? "spin" : ""}>{running ? "◌" : "▶"}</span>
                {running ? "Checking..." : "Run all checks"}
              </button>
            </div>
          </section>

          {activeNav === "Settings" ? (
            <section className="panel settings-panel">
              <div className="section-heading"><div><p className="section-overline">PREFERENCES</p><h2>Workspace settings</h2><p>Current project configuration.</p></div></div>
              {[
                ["Workspace name", "JavaShip"],
                ["Project language", "Java 21"],
                ["Build system", "Maven 3.9.15"],
                ["Dashboard theme", "Dark"],
                ["Project version", "1.0.0-SNAPSHOT"],
              ].map(([label, value]) => (
                <div className="settings-row" key={label}><div><strong>{label}</strong></div><span>{value}</span></div>
              ))}
            </section>
          ) : (
            <>
              <section className="metrics-grid">
                <MetricCard icon="◉" label="Release readiness" value={`${readiness}%`} note="Based on current demo results" tone="purple" />
                <MetricCard icon="✓" label="Checks passed" value={`${passed}/${checks.length}`} note="Completed successfully" tone="green" />
                <MetricCard icon="⌘" label="Build status" value={failed ? "Failed" : running ? "Running" : "Ready"} note="Demo build summary" tone="blue" />
                <MetricCard icon="⚠" label="Needs attention" value={warnings + failed + pending} note="Warnings and pending checks" tone="amber" />
              </section>

              <section className="dashboard-grid">
                <article className="panel readiness-panel">
                  <div className="section-heading">
                    <div><p className="section-overline">PROJECT HEALTH</p><h2>Release readiness</h2><p>How prepared is JavaShip for release?</p></div>
                    <button className="subtle-icon-button" onClick={runChecks} aria-label="Refresh checks">↻</button>
                  </div>

                  <div className="readiness-hero">
                    <div className="score-ring" style={{ "--score": `${readiness * 3.6}deg` }}>
                      <div className="score-ring-inner"><strong>{readiness}</strong><small>/100</small></div>
                    </div>
                    <div className="score-copy">
                      <span className={`score-status ${failed ? "text-danger" : warnings ? "text-warning" : "text-success"}`}>
                        <span className="status-dot" />{failed ? "Issues detected" : warnings ? "Review recommended" : "Looking good"}
                      </span>
                      <h3>{passed} checks passed</h3>
                      <p>{warnings + pending + failed} items need attention before release.</p>
                    </div>
                  </div>

                  <div className="progress-caption"><span>Overall completion</span><strong>{readiness}%</strong></div>
                  <div className="progress-track"><div className="progress-fill" style={{ width: `${readiness}%` }} /></div>

                  <div className="category-list">
                    {["Environment", "Build", "Tests", "Dependencies", "Git", "Security"].map((category) => {
                      const categoryChecks = checks.filter((item) => item.category === category);
                      const completed = categoryChecks.filter((item) => item.status === "Passed").length;
                      const percent = categoryChecks.length ? Math.round(completed / categoryChecks.length * 100) : 0;
                      return (
                        <div className="category-row" key={category}>
                          <span className="category-name">{category}</span>
                          <div className="category-progress"><div style={{ width: `${percent}%` }} /></div>
                          <strong>{percent}%</strong>
                        </div>
                      );
                    })}
                  </div>
                </article>

                <article className="panel checks-panel">
                  <div className="section-heading">
                    <div><p className="section-overline">VALIDATION</p><h2>Project checks</h2><p>Current release-readiness summary.</p></div>
                    <button className="text-link" onClick={() => navigate("Project Checks")}>View all →</button>
                  </div>

                  <div className="checks-summary">
                    <div><span className="summary-dot green-dot" /><strong>{passed}</strong><small>Passed</small></div>
                    <div><span className="summary-dot amber-dot" /><strong>{warnings}</strong><small>Warnings</small></div>
                    <div><span className="summary-dot grey-dot" /><strong>{pending}</strong><small>Pending</small></div>
                  </div>

                  <div className="check-list">
                    {filteredChecks.slice(0, activeNav === "Overview" ? 6 : checks.length).map((item) => (
                      <div className="check-row" key={item.name}>
                        <div className={`check-icon ${item.status.toLowerCase()}`}>{item.icon}</div>
                        <div className="check-copy"><strong>{item.name}</strong><span>{item.detail}</span></div>
                        <StatusBadge status={item.status} />
                      </div>
                    ))}
                    {filteredChecks.length === 0 && <p className="empty-state">No matching checks found.</p>}
                  </div>
                  <button className="full-width-link" onClick={() => navigate("Project Checks")}>Explore all project checks →</button>
                </article>
              </section>

              <section className="bottom-grid">
                <article className="panel project-panel">
                  <div className="section-heading"><div><p className="section-overline">PROJECT CONFIGURATION</p><h2>Project details</h2><p>Development environment summary.</p></div><div className="project-logo">⌘</div></div>
                  <div className="project-details">
                    {[
                      ["Project name", "JavaShip"],
                      ["Java version", "Java 21"],
                      ["Build tool", "Maven 3.9.15"],
                      ["Project version", "1.0.0-SNAPSHOT"],
                      ["Git branch", "main"],
                      ["Working tree", "Passed"],
                    ].map(([label, value]) => (
                      <div className="detail-row" key={label}><span>{label}</span>{label === "Working tree" ? <StatusBadge status={value} /> : <strong>{value}</strong>}</div>
                    ))}
                  </div>
                  <div className="project-footer"><span>Repository metadata shown as a demo</span><button onClick={() => navigate("Git & Release")}>View Git →</button></div>
                </article>

                <article className="panel activity-panel">
                  <div className="section-heading"><div><p className="section-overline">WORKFLOW</p><h2>Recent activity</h2><p>Release workflow overview.</p></div><span className="live-indicator"><span /> DEMO DATA</span></div>
                  <div className="activity-list">
                    {[
                      ["Java environment verified", "JDK configuration check", "Environment", "✓"],
                      ["Compilation check passed", "Maven compiler validation", "Build", "⌘"],
                      ["JUnit test suite completed", "Automated test validation", "Tests", "✓"],
                      ["Dependency review needed", "Review dependency configuration", "Dependencies", "!"],
                    ].map(([title, description, time, icon]) => (
                      <div className="activity-row" key={title}>
                        <div className="activity-icon">{icon}</div>
                        <div className="activity-copy"><strong>{title}</strong><span>{description}</span></div>
                        <span className="activity-time">{time}</span>
                      </div>
                    ))}
                  </div>
                  <div className="last-run"><span>◷</span><span>Last run</span><strong>{lastRun}</strong></div>
                </article>
              </section>

              <section className="release-banner">
                <div className="release-banner-icon">✦</div>
                <div><strong>Keep your next release on track</strong><p>Review warnings and validate your project before packaging.</p></div>
                <button onClick={() => navigate("Git & Release")}>Review release →</button>
              </section>
            </>
          )}

          <footer className="page-footer"><span>JavaShip / Developer workspace</span><span><span className="online-dot" /> Built for release confidence</span><span>v1.0.0 · 2026</span></footer>
        </div>
      </main>
    </div>
  );
}

export default App;
