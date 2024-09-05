# Contributing to Renshuu Widget for Android

Thank you for considering contributing to the **Renshuu Widget for Android** project! This guide outlines how you can contribute to the project and the guidelines we follow to ensure a smooth and collaborative experience for everyone involved.

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [How to Contribute](#how-to-contribute)
- [Reporting Issues](#reporting-issues)
- [Suggesting Enhancements](#suggesting-enhancements)
- [Pull Requests](#pull-requests)
- [Running Detekt](#running-detekt)
- [Style Guide](#style-guide)

## Code of Conduct

This project follows a [Code of Conduct](CODE_OF_CONDUCT.md) to ensure a welcoming and inclusive environment. Please read it before contributing.

## How to Contribute

1. **Fork the project** to your GitHub account.
2. **Clone the repository**:
   ```bash
   git clone https://github.com/EmmanueleVilla/renshuu-widget-android.git
   cd renshuu-widget-android
   ```
3. **Create your feature branch**:
   ```bash
   git checkout -b feature/AmazingFeature
   ```
4. **Make your changes** and commit them:
   ```bash
   git commit -m "Add some AmazingFeature"
   ```
5. **Run Detekt** to ensure code quality (see [Running Detekt](#running-detekt) for instructions).
6. **Push to the branch**:
   ```bash
   git push origin feature/AmazingFeature
   ```
7. **Open a pull request** to the `main` branch.

## Reporting Issues

If you find a bug or have a feature request, feel free to open an [issue on GitHub](https://github.com/EmmanueleVilla/renshuu_widget/issues). Please provide as much detail as possible to help us reproduce the issue or understand your suggestion.

## Suggesting Enhancements

Enhancements and suggestions are always welcome! You can propose a new feature by opening an issue and explaining how it improves the project. Please follow the issue template for feature requests.

## Pull Requests

We welcome pull requests from the community! Please ensure that:

- Your code follows the style guide outlined below.
- You have run Detekt and fixed any issues (see [Running Detekt](#running-detekt)).
- Your pull request provides a clear description of what has been changed and why.

### Running Detekt

Before submitting a pull request, you need to run [Detekt](https://detekt.dev/) to check your code for issues and ensure it adheres to our coding standards. Detekt checks are mandatory for the PR to be merged.

To run Detekt:

1. Run the following command in the project directory:
   ```bash
   ./gradlew detekt
   ```
2. Fix any issues reported by Detekt before submitting your pull request.

## Style Guide

Please follow the existing code style and use meaningful commit messages. You should also auto-format the files before committing them to ensure a proper GIT file history!
